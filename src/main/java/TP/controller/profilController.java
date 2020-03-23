package TP.controller;


import TP.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class profilController {

    @Autowired
    TrainerService trainerService;

    @GetMapping(value = "/profile")
    public ModelAndView profil(){
        var model = new ModelAndView();
        model.setViewName("profile");
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addObject("trainerProfile", trainerService.getTrainer(loggedUser.getUsername()));
        return model;
    }
}
