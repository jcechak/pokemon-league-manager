package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import java.util.Collection;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This is a Spring MVC controller for managing pokemon related requests.
 *
 * @author Jaroslav Cechak
 */
@Controller
public class PokemonController {

    public static final String CREATE_URI = WebApiUris.POKEMON_URI + "/create";
    public static final String NEW_FORM_URI = WebApiUris.POKEMON_URI + "/newform";
    public static final String VIEW_URI = WebApiUris.POKEMON_URI + "/view";
    public static final String CHANGE_SKILL_URI = WebApiUris.POKEMON_URI + "/changeskill";
    public static final String CHANGE_TRAINER_URI = WebApiUris.POKEMON_URI + "/changetrainer";
    public static final String TRADE_URI = WebApiUris.POKEMON_URI + "/trade";
    public static final String DELETE_URI = WebApiUris.POKEMON_URI + "/delete";
    public static final String LIST_URI = WebApiUris.POKEMON_URI + "/list";
    public static final String FIND_WITH_TRAINER_URI = WebApiUris.POKEMON_URI + "/withtrainer";
    public static final String FIND_WITH_NAME_URI = WebApiUris.POKEMON_URI + "/withname";
    public static final String FIND_WITH_TYPE_URI = WebApiUris.POKEMON_URI + "/withtype";

    @Inject
    private PokemonFacade pokemonFacade;

    @Inject
    private TrainerFacade trainerFacade;

    @ModelAttribute("trainers")
    public Collection<TrainerDTO> trainers() {
        return trainerFacade.findAllTrainers();
    }

    @ModelAttribute("types")
    public PokemonType[] types() {
        return PokemonType.values();
    }

    @ModelAttribute("pokemonWrapper")
    public PokemonCreateDTO pokemonCreate() {
        return new PokemonCreateDTO();
    }

    @ModelAttribute("authenticatedUser")
    public Authentication authenticatedUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = WebApiUris.POKEMON_URI, method = RequestMethod.GET)
    public String defaultPage() {
        return "redirect:" + LIST_URI;
    }

    @RequestMapping(value = CREATE_URI, method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute("pokemonWrapper") PokemonCreateDTO formBean,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                System.err.println("ObjectError: " + ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                System.err.println("FieldError: " + fe);
            }
            System.err.println("error create");
            model.addAttribute("alert_danger", "Unable to create pokemon. Some fields has not been filled in corectly.");
            return NEW_FORM_URI;
        }
        Long id;
        try {
            id = pokemonFacade.createPokemon(formBean);
            redirectAttributes.addAttribute("id", id);
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            model.addAttribute("alert_danger", "Unable to create pokemon. There has been an error in the system.");
            return NEW_FORM_URI;
        }

        redirectAttributes.addFlashAttribute("alert_success", "Pokemon has been successfully created.");        
        return "redirect:" + VIEW_URI + "/{id}";
    }

    @RequestMapping(value = NEW_FORM_URI, method = RequestMethod.GET)
    public String newForm() {
        return NEW_FORM_URI;
    }

    @RequestMapping(value = VIEW_URI + "/{id}", method = RequestMethod.GET)
    public String view(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (id == null) {
            System.err.println("id is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to retrieve pokemon. The given pokemon is invalid.");
            return "redirect:" + LIST_URI;
        }

        try {
            model.addAttribute("pokemon", pokemonFacade.getPokemonById(id));
            model.addAttribute("trainer", trainerFacade.findTrainerById(pokemonFacade.getPokemonById(id).getTrainerId()));
            model.addAttribute("pokemons", pokemonFacade.getAllPokemons());
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to retrieve pokemon. There has been an error in the system.");
            return "redirect:" + LIST_URI;
        }
        return VIEW_URI;
    }

    @RequestMapping(value = CHANGE_SKILL_URI + "/{id}", method = RequestMethod.POST)
    public String changeSkill(
            @PathVariable Long id,
            @ModelAttribute("pokemonWrapper") PokemonCreateDTO pokemonWrapper,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (id == null) {
            System.err.println("id is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to change skill. The given pokemon is invalid.");
            return "redirect:" + LIST_URI;
        }
        if (pokemonWrapper == null) {
            System.err.println("wrapper is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to change skill. The new skill has not been selected properly.");
            return "redirect:" + VIEW_URI + "/{id}";
        }

        if (pokemonWrapper.getSkillLevel() < 0) {
            System.err.println("to low skill");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to change skill. The new skill has not been selected properly.");
            return "redirect:" + VIEW_URI + "/{id}";
        }

        try {
            pokemonFacade.changeSkill(id, pokemonWrapper.getSkillLevel());
            model.addAttribute("pokemon", pokemonFacade.getPokemonById(id));
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to change skill. There has been an error in the system.");
            return "redirect:" + VIEW_URI + "/{id}";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Pokemon's skill has been successfully changed.");
        return "redirect:" + VIEW_URI + "/{id}";
    }

    @RequestMapping(value = CHANGE_TRAINER_URI + "/{id}", method = RequestMethod.POST)
    public String changeTrainer(
            @PathVariable Long id,
            @ModelAttribute("pokemonWrapper") PokemonCreateDTO pokemonWrapper,
            RedirectAttributes redirectAttributes
    ) {
        if (id == null) {
            System.err.println("id is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to change trainer. The given pokemon is invalid.");
            return "redirect:" + LIST_URI;
        }

        if (pokemonWrapper == null || pokemonWrapper.getTrainerId() == null) {
            System.err.println("trainer id is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to change trainer. Trainer has not been selected properly.");
            return "redirect:" + VIEW_URI + "/{id}";
        }

        try {
            pokemonFacade.changeTrainer(id, pokemonWrapper.getTrainerId());
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to change trainer. There has been an error in the system.");
            return "redirect:" + VIEW_URI + "/{id}";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Trainer has been changed.");
        return "redirect:" + VIEW_URI + "/{id}";
    }

    @RequestMapping(value = TRADE_URI + "/{id1}/{id2}", method = RequestMethod.GET)
    public String trade(
            @PathVariable Long id1,
            @PathVariable Long id2,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (id1 == null) {
            System.err.println("id1 is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to trade pokemons. The given pokemon is invalid.");
            return "redirect:" + LIST_URI;
        }

        if (id2 == null) {
            System.err.println("id2 is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to trade pokemons. The given pokemon is invalid.");
            return "redirect:" + VIEW_URI + "/{id1}";
        }

        if (id1.equals(id2)) {
            System.err.println("same ids");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to trade pokemons. The given pokemons are the same.");
            return "redirect:" + VIEW_URI + "/{id1}";
        }

        try {
            pokemonFacade.tradePokemon(id1, id2);
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to trade pokemons. There has been an error in the system.");
            return "redirect:" + VIEW_URI + "/{id1}";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Pokemons has been successfully traded.");
        return "redirect:" + VIEW_URI + "/{id1}";
    }

    @RequestMapping(value = DELETE_URI + "/{id}", method = RequestMethod.GET)
    public String delete(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        if (id == null) {
            System.err.println("id is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to delete pokemon. The given pokemon is invalid.");
            return "redirect:" + LIST_URI;
        }

        try {
            pokemonFacade.deletePokemon(id);
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to delete pokemon. There has been an error in the system.");
            return "redirect:" + LIST_URI;
        }

        redirectAttributes.addFlashAttribute("alert_success", "Pokemon has been successfully deleted.");
        return "redirect:" + LIST_URI;

    }

    @RequestMapping(value = LIST_URI, method = RequestMethod.GET)
    public String findAll(
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            model.addAttribute("pokemons", pokemonFacade.getAllPokemons());
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to retrieve pokemons. There has been an error in the system.");
            return WebApiUris.MENU_URI;
        }
        return LIST_URI;
    }

    @RequestMapping(value = FIND_WITH_TRAINER_URI + "/{trainerId}", method = RequestMethod.GET)
    public String findWithTrainer(
            @PathVariable Long trainerId,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (trainerId == null) {
            System.err.println("id is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to retrieve pokemons. Given trainer is invalid.");
            return "redirect:" + LIST_URI;
        }

        try {
            model.addAttribute("pokemons", pokemonFacade.getAllPokemonsOfTrainerWithId(trainerId));
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to retrieve pokemons. There has been an error in the system.");
            return "redirect:" + LIST_URI;
        }
        return LIST_URI;
    }

    @RequestMapping(value = FIND_WITH_NAME_URI, method = RequestMethod.POST)
    public String findWithName(
            @ModelAttribute("pokemonWrapper") PokemonCreateDTO pokemonWrapper,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (pokemonWrapper == null || pokemonWrapper.getName() == null) {
            System.err.println("name is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to retrieve pokemons. Given pokemon name is invalid.");
            return "redirect:" + LIST_URI;
        }

        try {
            model.addAttribute("pokemons", pokemonFacade.getAllPokemonsWithName(pokemonWrapper.getName()));
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to retrieve pokemons. There has been an error in the system.");
            return "redirect:" + LIST_URI;
        }
        return LIST_URI;
    }

    @RequestMapping(value = FIND_WITH_TYPE_URI, method = RequestMethod.POST)
    public String findWithType(
            @ModelAttribute("pokemonWrapper") PokemonCreateDTO pokemonWrapper,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (pokemonWrapper == null || pokemonWrapper.getType() == null) {
            System.err.println("type is null");
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to retrieve pokemons. Given pokemon type is invalid.");
            return "redirect:" + LIST_URI;
        }

        try {
            model.addAttribute("pokemons", pokemonFacade.getAllPokemonsWithType(pokemonWrapper.getType()));
        } catch (Exception ex) {
            System.err.println("chyba na service" + ex.getMessage());
            redirectAttributes.addFlashAttribute("alert_danger", "Unable to retrieve pokemons. There has been an error in the system.");
            return "redirect:" + LIST_URI;
        }
        return LIST_URI;
    }
}
