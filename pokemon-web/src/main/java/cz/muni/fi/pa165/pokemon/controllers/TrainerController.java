package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Controller for trainer administration.
 * @author Marek Sabo
 */

@Controller
@RequestMapping("/menu")
public class TrainerController {

    @Inject
    private TrainerFacade trainerFacade;

    @Inject
    private BadgeFacade badgeFacade;

    @Inject
    private StadiumFacade stadiumFacade;

    @Inject
    private PokemonFacade pokemonFacade;

    @RequestMapping(value = "/trainer/trainerList")
    public String showList(Model model) {
        Collection<TrainerDTO> trainerDTOs = trainerFacade.findAllTrainers();
        model.addAttribute("trainers", trainerDTOs);
        return "/menu/trainer/trainerList";
    }

}
