package TP.controller;


import TP.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping(value ="/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
