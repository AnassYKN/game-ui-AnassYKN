package TP;

import TP.bo.PokemonType;
import TP.service.impl.PokemonTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PokemonTypeServiceImplTest {
    @Test
    void listPokemonsTypes_shouldCallTheRemoteService() {
        var url = "https://pokemon-type-api-anassykn.herokuapp.com/";

        var restTemplate = mock(RestTemplate.class);
        var pokemonServiceImpl = new PokemonTypeServiceImpl();
        pokemonServiceImpl.setRestTemplate(restTemplate);
        pokemonServiceImpl.setPokemonTypeServiceUrl(url);

        var pikachu = new PokemonType();
        pikachu.setName("pikachu");
        pikachu.setId(25);

        var expectedUrl = "https://pokemon-type-api-anassykn.herokuapp.com/pokemon-types/";
        when(restTemplate.getForObject(expectedUrl, PokemonType[].class)).thenReturn(new PokemonType[]{pikachu});

        var pokemons = pokemonServiceImpl.listPokemonsTypes();

        assertNotNull(pokemons);
        assertEquals(1, pokemons.size());

        verify(restTemplate).getForObject(expectedUrl, PokemonType[].class);
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
}
