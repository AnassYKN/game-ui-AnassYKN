package TP.service;

import TP.bo.PokemonType;
import TP.bo.Trainer;

import java.util.List;

public interface TrainerService {

    public List<Trainer> listTrainer();

    Trainer getTrainer(String TrainerName);
}
