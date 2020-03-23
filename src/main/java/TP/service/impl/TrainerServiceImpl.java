package TP.service.impl;
import TP.bo.PokemonType;
import TP.bo.Trainer;
import TP.service.PokemonTypeService;
import TP.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    private RestTemplate restTemplate;
    private String TrainerUrl;

    @Autowired
    private PokemonTypeService pokemonTypeService;

    @Override
    public List<Trainer> listTrainer() {
        Trainer[] listTrainer = this.restTemplate.getForObject(TrainerUrl+"/trainers/", Trainer[].class);
        for (Trainer trainer:listTrainer){
            getPokemonTypeByTrainer(trainer);
        }
        return List.of(listTrainer);
    }

    @Override
    public Trainer getTrainer(String TrainerName) {
        Trainer trainer = this.restTemplate.getForObject(TrainerUrl+"/trainers/" + TrainerName, Trainer.class);
        return trainer;
    }

    @Override
    public void getPokemonTypeByTrainer(Trainer trainer) {
        List<PokemonType> pokemonsTypes = new ArrayList<>();
        for(PokemonType pokemon: trainer.getTeam()){
            PokemonType pokemonType = pokemonTypeService.getPokemonTypeByid(pokemon.getPokemonTypeId());
            pokemon.setStats(pokemonType.getStats());
            pokemon.setSprites(pokemonType.getSprites());
            pokemon.setName(pokemonType.getName());
            pokemonsTypes.add(pokemon);
        }
        trainer.setTeam(pokemonsTypes);
    }



    @Autowired
    @Qualifier("trainerApiRestTemplate")
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
    }

    @Value("${trainer.service.url}")
    public void setTrainerServiceUrl(String TrainerUrl) {
        this.TrainerUrl = TrainerUrl;
    }
}
