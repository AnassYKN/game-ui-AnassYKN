package TP.service;

import TP.bo.PokemonType;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public interface PokemonTypeService {
    List<PokemonType> listPokemonsTypes();
}
