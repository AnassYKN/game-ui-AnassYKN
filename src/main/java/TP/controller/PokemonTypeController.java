package TP.controller;

import TP.bo.PokemonType;
import TP.service.PokemonTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PokemonTypeController {

    PokemonTypeService pokemonTypeService;

    @GetMapping("/pokedex")
    public ModelAndView pokedex(){

        var mav = new ModelAndView();
        mav.setViewName("pokedex");
        List<PokemonType> pokemonlist = pokemonTypeService.listPokemonsTypes();
        mav.addObject("pokemonTypes",pokemonlist);
        return mav;

    }

    public void setPokemonTypeService(PokemonTypeService pokemonTypeService) {
        this.pokemonTypeService=pokemonTypeService;
    }
}
