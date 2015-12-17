package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for trainer administration.
 * @author Marek Sabo
 */

@Controller
@RequestMapping("/menu")
public class TrainerController {

    public static final Logger log = LoggerFactory.getLogger(TrainerController.class);

    @Inject
    private TrainerFacade trainerFacade;

    @Inject
    private BadgeFacade badgeFacade;

    @Inject
    private StadiumFacade stadiumFacade;

    @Inject
    private PokemonFacade pokemonFacade;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }


    @RequestMapping(value = "/trainer/list")
    public String showList(Model model) {
        Collection<TrainerDTO> trainerDTOs = trainerFacade.findAllTrainers();
        model.addAttribute("trainers", trainerDTOs);
        return "/menu/trainer/list";
    }

    @RequestMapping(value = "/trainer/new", method = RequestMethod.GET)
    public String newTrainer(Model model) {
        model.addAttribute("new", new TrainerDTO());
        log.debug("trainer NEW called");
        return "/menu/trainer/new";
    }


    @ModelAttribute("pokemons")
    public Collection<PokemonDTO> pokemons() {
        return pokemonFacade.getAllPokemons();
    }

    @ModelAttribute("stadiums")
    public Collection<StadiumDTO> stadiums() {
        return stadiumFacade.findAll();
    }


    @RequestMapping(value = "/trainer/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("new") TrainerDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                System.out.println("ObjectError: {}" + ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                System.out.println("FieldError: {}" + fe);
            }
            return "/menu/trainer/new";
        }
        trainerFacade.createTrainer(formBean);
        Long id = formBean.getId();
        System.out.println("create trainer " + formBean.toString());
        redirectAttributes.addFlashAttribute("alert_success", "Trainer was created");
        return "redirect:" + uriBuilder.path("/menu/trainer/view/{id}").buildAndExpand(id).encode().toUriString();
    }

    @RequestMapping(value = "/trainer/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        System.out.println("DEBUG " + id + ' ' + model);
        Map<Long, StadiumDTO> badgesAndStadiums = new HashMap<>();
        for(BadgeDTO b : badgeFacade.getAllBadges()) {
            System.out.println("DEBUG " + b);
            badgesAndStadiums.put(b.getId(), stadiumFacade.findById(b.getStadiumId()));
        }
        model.addAttribute("trainer", trainerFacade.findTrainerById(id));
        model.addAttribute("stadiumsMap", badgesAndStadiums);
        System.out.println("DEBUG" + trainerFacade.findTrainerById(id).toString());
        return "/menu/trainer/view";
    }

    @RequestMapping(value = "/trainer/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable long id, Model model) {
        System.out.println("DEBUG " + id + ' ' + model);

        model.addAttribute("trainer", trainerFacade.findTrainerById(id));
        System.out.println("DEBUG" + trainerFacade.findTrainerById(id).toString());
        return "/menu/trainer/edit";
    }

    @RequestMapping(value = "/trainer/update", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("update") TrainerDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                System.out.println("ObjectError: {}" + ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                System.out.println("FieldError: {}" + fe);
            }
            model.addAttribute("trainer", trainerFacade.findTrainerById(formBean.getId()));
            return "/menu/trainer/edit";
        }
        trainerFacade.updateTrainer(formBean);
        System.out.println("update trainer " + formBean.toString());
        redirectAttributes.addFlashAttribute("alert_success", "Trainer was updated");
        return "redirect:" + uriBuilder.path("/menu/trainer/list").toUriString();
    }


    @RequestMapping(value = "/trainer/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        TrainerDTO trainerDTO = trainerFacade.findTrainerById(id);
        trainerFacade.deleteTrainer(trainerDTO);
        redirectAttributes.addFlashAttribute("alert_success", "Trainer was deleted successfully.");
        return "redirect:" + uriBuilder.path("/menu/trainer/list").toUriString();
    }

}
