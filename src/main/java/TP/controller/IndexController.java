package TP.controller;

import TP.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    TrainerService trainerService;

    @GetMapping("/")
    public ModelAndView index() {
        var model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addObject("trainerProfile", trainerService.getTrainer(loggedUser.getUsername()));
            model.setViewName("profile");

            return model;
        }else {
            model.setViewName("index");
            return model;
        }

    }

    @PostMapping("/registerTrainer")
    public ModelAndView registerNewTrainer(String trainerName) {
        var mav = new ModelAndView();
        mav.setViewName("register");
        mav.addObject("profile", trainerName);
        return mav;
    }
}
