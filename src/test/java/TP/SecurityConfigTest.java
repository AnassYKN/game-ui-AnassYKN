package TP;

import TP.bo.Trainer;
import TP.config.SecurityConfig;
import TP.service.TrainerService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SecurityConfigTest {
    @Test
    void securityConfig_shouldExtendWebSecurityConfigurerAdapter(){
        assertTrue(WebSecurityConfigurerAdapter.class.isAssignableFrom(SecurityConfig.class));
    }

    @Test
    void passwordEncoder_shouldBeBCryptPasswordEncoder() {
        var securityConfig = new SecurityConfig();
        var passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertEquals(BCryptPasswordEncoder.class, passwordEncoder.getClass());
    }

    @Test
    void userDetailsService_shouldUseTrainerService() {
        var securityConfig = new SecurityConfig();

        var trainerService = mock(TrainerService.class);
        var trainer = new Trainer();
        trainer.setName("Garry");
        trainer.setPassword("secret");
        when(trainerService.getTrainer("Garry")).thenReturn(trainer);

        securityConfig.setTrainerService(trainerService);

        var userDetailsService = securityConfig.userDetailsService();

        var garry = userDetailsService.loadUserByUsername("Garry");

        // mock should be called
        verify(trainerService).getTrainer("Garry");

        assertNotNull(garry);
        assertEquals("Garry", garry.getUsername());
        assertEquals("secret", garry.getPassword());
        assertTrue(garry.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void userDetailsService_shouldThrowABadCredentialsException_whenUserDoesntExists() {
        var securityConfig = new SecurityConfig();

        // the mock returns null
        var trainerService = mock(TrainerService.class);
        securityConfig.setTrainerService(trainerService);

        var userDetailsService = securityConfig.userDetailsService();

        var exception = assertThrows(BadCredentialsException.class, () -> userDetailsService.loadUserByUsername("Garry"));
        assertEquals("No such user", exception.getMessage());

        // mock should be called
        verify(trainerService).getTrainer("Garry");
    }
}
