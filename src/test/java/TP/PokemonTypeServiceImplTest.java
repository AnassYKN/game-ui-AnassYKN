package TP;

import TP.bo.PokemonType;
import TP.bo.Trainer;
import TP.service.impl.PokemonTypeServiceImpl;
import TP.service.impl.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PokemonTypeServiceImplTest {
    @Autowired
    PokemonTypeServiceImpl pokemonTypeService;

    @Mock
    RestTemplate restTemplate;

    String expectedUrl="https://pokemon-type-api-anassykn.herokuapp.com/pokemon-types/{id}";

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        pokemonTypeService.setRestTemplate(restTemplate);

        var pikachu = new PokemonType();
        pikachu.setPokemonTypeId(25);
        pikachu.setName("Pikachu");
        when(restTemplate.getForObject(expectedUrl, PokemonType.class, 25)).thenReturn(pikachu);
    }

    @Test
    void listPokemonsTypes_shouldCallTheRemoteService() {
        var url = "https://pokemon-type-api-anassykn.herokuapp.com";

        var restTemplate = mock(RestTemplate.class);
        var pokemonServiceImpl = new PokemonTypeServiceImpl();
        pokemonServiceImpl.setRestTemplate(restTemplate);
        pokemonServiceImpl.setPokemonTypeServiceUrl(url);

        var pikachu = new PokemonType();
        pikachu.setName("pikachu");
        pikachu.setPokemonTypeId(25);

        var expectedUrl = "https://pokemon-type-api-anassykn.herokuapp.com/pokemon-types/";
        when(restTemplate.getForObject(expectedUrl, PokemonType[].class)).thenReturn(new PokemonType[]{pikachu});

        var pokemons = pokemonServiceImpl.listPokemonsTypes();

        assertNotNull(pokemons);
        assertEquals(1, pokemons.size());

        verify(restTemplate).getForObject(expectedUrl, PokemonType[].class);
    }

    @Test
    void listTrainerTypes_shouldCallTheRemoteService() {
        var url = "https://trainer-api-anassykn.herokuapp.com";

        var restTemplate = mock(RestTemplate.class);
        var trainerServiceImpl = new TrainerServiceImpl();
        trainerServiceImpl.setRestTemplate(restTemplate);
        trainerServiceImpl.setTrainerServiceUrl(url);

        var trainer = new Trainer();
        trainer.setName("Anass");

        var expectedUrl = "https://trainer-api-anassykn.herokuapp.com/trainers/";
        when(restTemplate.getForObject(expectedUrl, Trainer[].class)).thenReturn(new Trainer[]{trainer});

        var trainers = trainerServiceImpl.listTrainer();

        assertNotNull(trainers);
        assertEquals(1, trainers.size());

        verify(restTemplate).getForObject(expectedUrl, Trainer[].class);
    }

    @Test
    void pokemonServiceImpl_shouldBeAnnotatedWithService(){
        assertNotNull(PokemonTypeServiceImpl.class.getAnnotation(Service.class));
    }

    @Test
    void setRestTemplate_shouldBeAnnotatedWithAutowired() throws NoSuchMethodException {
        var setRestTemplateMethod = PokemonTypeServiceImpl.class.getDeclaredMethod("setRestTemplate", RestTemplate.class);
        assertNotNull(setRestTemplateMethod.getAnnotation(Autowired.class));
    }
    @Test
    void setPokemonServiceUrl_shouldBeAnnotatedWithValue() throws NoSuchMethodException {
        var setter = PokemonTypeServiceImpl.class.getDeclaredMethod("setPokemonTypeServiceUrl", String.class);
        var valueAnnotation = setter.getAnnotation(Value.class);
        assertNotNull(valueAnnotation);
        assertEquals("${pokemonType.service.url}", valueAnnotation.value());
    }



    @Test
    void getPokemonType_shouldUseCache() {
        pokemonTypeService.getPokemonTypeByid(25);

        // rest template should have been called once
        verify(restTemplate).getForObject(expectedUrl, PokemonType.class, 25);

        pokemonTypeService.getPokemonTypeByid(25);

        // rest template should not be called anymore because result is in cache !
        verifyNoMoreInteractions(restTemplate);

        // one result should be in cache !
        var cachedValue = cacheManager.getCache("pokemon-types").get(25).get();
        assertNotNull(cachedValue);
        assertEquals(PokemonType.class, cachedValue.getClass());
        assertEquals("Pikachu", ((PokemonType)cachedValue).getName());
    }
}
