package TP.service.impl;
import TP.bo.Trainer;
import TP.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    private RestTemplate restTemplate;
    private String TrainerUrl;
    @Override
    public List<Trainer> listTrainer() {
        Trainer[] listTrainer = this.restTemplate.getForObject(TrainerUrl+"/trainers/", Trainer[].class);
        return List.of(listTrainer);
    }

    @Override
    public Trainer getTrainer(String TrainerName) {
        Trainer trainer = this.restTemplate.getForObject(TrainerUrl+"/trainers/" + TrainerName, Trainer.class);
        return trainer;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
    }

    @Value("https://trainer-api-anassykn.herokuapp.com")
    public void setPokemonTypeServiceUrl(String TrainerUrl) {
        this.TrainerUrl = TrainerUrl;
    }
}
