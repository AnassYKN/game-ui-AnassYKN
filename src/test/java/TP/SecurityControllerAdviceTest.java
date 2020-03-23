package TP;

import TP.controller.SecurityControllerAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityControllerAdviceTest {

    @Test
    void securityControllerAdvice_shouldBeAControllerAdvice() {
        assertNotNull(SecurityControllerAdvice.class.getAnnotation(ControllerAdvice.class));
    }

    @Test
    void principal_shouldUseModelAttribute() throws NoSuchMethodException {
        var principalMethod = SecurityControllerAdvice.class.getDeclaredMethod("principal");
        var annotation = principalMethod.getAnnotation(ModelAttribute.class);
        assertNotNull(annotation);
        assertEquals("user", annotation.value());
    }

    @Test
    void principal_shouldAddThePrincipalToTheModel() throws NoSuchMethodException {
        var advice = new SecurityControllerAdvice();

        // mocking data
        var authentication = mock(Authentication.class);
        var user = mock(User.class);
        when(authentication.getPrincipal()).thenReturn(user);

        // setting security to the mocked auth !
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var result = advice.principal();
        assertEquals(user, result);
    }

}

