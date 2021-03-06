package TP.service.impl;

import TP.bo.PokemonType;
import TP.service.PokemonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {

    private RestTemplate restTemplate;
    private String pokemonServiceUrl;

    final HttpHeaders headers = new HttpHeaders();
        //headers.set("Accept-Language", "fr");

    //Create a new HttpEntity
    final HttpEntity<String> entity = new HttpEntity<String>(headers);


    public List<PokemonType> listPokemonsTypes() {
        PokemonType[] listPokemon = this.restTemplate.getForObject(pokemonServiceUrl+"/pokemon-types/", PokemonType[].class,entity);
        return List.of(listPokemon);
    }

    @Override
    public PokemonType getPokemonTypeByid(int id) {
        PokemonType pokemonType = this.restTemplate.getForObject(pokemonServiceUrl+"/pokemon-types/"+id, PokemonType.class);
        return pokemonType;
    }


    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
    }

    @Value("https://pokemon-type-api-anassykn.herokuapp.com")
    public void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.pokemonServiceUrl = pokemonServiceUrl;
    }
}
