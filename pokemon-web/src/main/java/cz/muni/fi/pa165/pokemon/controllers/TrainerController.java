package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.exceptions.PokemonServiceException;
import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.*;

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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    /**
     * Attribute 'availableStadiums' contains trainers with stadiums where they can still get badge.
     * Not sure where the logic of retrieving all stadiums should be.
     */
    @RequestMapping(value = "/trainer/list")
    public String showList(@ModelAttribute("alert_success") String alertSuccess, Model model) {
        Collection<TrainerDTO> trainerDTOs = trainerFacade.findAllTrainers();
        Map<Long,Collection<StadiumDTO>> mapTrainerStadiums = new HashMap<>();
        Collection<StadiumDTO> incorrectStadiumDTOs;
        for (TrainerDTO t : trainerDTOs) {
            incorrectStadiumDTOs = new HashSet<>();
            for (BadgeDTO b : t.getBadges()) {
                incorrectStadiumDTOs.add(stadiumFacade.findById(b.getStadiumId()));
            }
            if (t.getStadium() != null) {
                incorrectStadiumDTOs.add(t.getStadium());
            }
            Collection<StadiumDTO> dtos = stadiumFacade.findAll();
            dtos.removeAll(incorrectStadiumDTOs);
            mapTrainerStadiums.put(t.getId(), dtos);
        }
        model.addAttribute("trainers", trainerDTOs);
        model.addAttribute("availableStadiums", mapTrainerStadiums);
        model.addAttribute("badgesCount", badgesCount());
        return "/menu/trainer/list";
    }

    private Map<Long, Integer> badgesCount() {
        Map<Long, Integer> map = new HashMap<>();
        for (TrainerDTO t : trainerFacade.findAllTrainers()) {
            map.put(t.getId(), t.getBadges().size());
        }
        return map;
    }

    @RequestMapping(value = "/trainer/new", method = RequestMethod.GET)
    public String newTrainer(Model model) {
        model.addAttribute("new", new TrainerDTO());
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
            redirectAttributes.addFlashAttribute("alert_error", "Trainer was not created.");
            return "/menu/trainer/new";
        }
        trainerFacade.createTrainer(formBean);
        Long id = formBean.getId();
        System.out.println("create trainer " + formBean.toString());
        redirectAttributes.addFlashAttribute("alert_success", "Trainer was created successfully.");
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
            redirectAttributes.addFlashAttribute("alert_error", "There was a problem with updating trainer.");
            return "redirect:/menu/trainer/edit/" + formBean.getId();
        }
        try {
            trainerFacade.updateTrainer(formBean);
        } catch (PokemonServiceException | DataIntegrityViolationException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("alert_error", "There was a problem with updating trainer: " + ex.getMessage());
            return "redirect:/menu/trainer/edit";
        }
        System.out.println("trainer updated " + formBean.toString());
        redirectAttributes.addFlashAttribute("alert_success", "Trainer was updated successfully.");

        return "redirect:" + uriBuilder.path("/menu/trainer/list").toUriString();
    }

    @RequestMapping(value = "/trainer/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        TrainerDTO trainerDTO = trainerFacade.findTrainerById(id);
        try {
            Collection<PokemonDTO> pokemons = pokemonFacade.getAllPokemonsOfTrainerWithId(id);
            for (PokemonDTO pokemonDTO : pokemons) {
                pokemonFacade.deletePokemon(pokemonDTO.getId());
            }
            Collection<BadgeDTO> badges = badgeFacade.getBadgesWithTrainer(trainerDTO);
            for(BadgeDTO b : badges) {
                    badgeFacade.removeBadge(b);
            }
            trainerFacade.deleteTrainer(trainerDTO);
        } catch (PokemonServiceException | DataIntegrityViolationException | IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("alert_error", "Trainer not deleted: " + ex.getMessage());
            return "redirect:/menu/trainer/list";
        }
        redirectAttributes.addFlashAttribute("alert_success", "Trainer was deleted successfully.");
        return "redirect:" + uriBuilder.path("/menu/trainer/list").toUriString();
    }

    @ModelAttribute("userName")
    public String userName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

}
