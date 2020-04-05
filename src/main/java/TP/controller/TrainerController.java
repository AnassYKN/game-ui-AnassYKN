package TP.controller;
import TP.bo.Trainer;
import TP.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TrainerController {

    @Autowired
    TrainerService TrainerService;

    @GetMapping("/trainers")
    public ModelAndView trainers(){
        var model = new ModelAndView();
        model.setViewName("trainers");
        List<Trainer> trainerlist = this.TrainerService.listTrainer();
        model.addObject("Trainers", trainerlist);
        return model;
    }
}
