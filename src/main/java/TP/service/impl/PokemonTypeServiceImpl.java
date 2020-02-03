package TP.service.impl;

import TP.bo.PokemonType;
import TP.service.PokemonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {



    private RestTemplate restTemplate;
    private String pokemonServiceUrl;


    public List<PokemonType> listPokemonsTypes() {
        PokemonType[] listPokemon = this.restTemplate.getForObject(pokemonServiceUrl+"/pokemon-types/", PokemonType[].class);
        return List.of(listPokemon);
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
    }

    @Value("${pokemonType.service.url}")
    public void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.pokemonServiceUrl = pokemonServiceUrl;
    }
}
