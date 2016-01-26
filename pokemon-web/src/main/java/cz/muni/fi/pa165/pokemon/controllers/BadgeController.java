package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.exceptions.PokemonServiceException;
import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Controller for badge administration.
 * @author Milos Bartak
 */
@Controller
@RequestMapping("/menu")
public class BadgeController {
    
    @Autowired
    private BadgeFacade badgeFacade;
    
    @Autowired
    private StadiumFacade stadiumFacade;
    
    @Autowired
    private TrainerFacade trainerFacade;
    
    
    public void setBadgeFacade(BadgeFacade badgeFacade) {
        this.badgeFacade = badgeFacade;
    }
    
    @ModelAttribute("typez")
    public PokemonType[] types() {
        return PokemonType.values();
    }
    
    @ModelAttribute("stadium")
    public StadiumDTO stadiumCreate() {
        return new StadiumDTO();
    }
    
    @RequestMapping(value = "/badge/badgeList")
    public String showList(Model model, HttpServletRequest request) {
        Collection<BadgeDTO> badges = badgeFacade.getAllBadges();
        Map<Long, StadiumDTO> badgesAndStadiums = new HashMap<>();
        for(BadgeDTO b:badges) {
            System.out.println("DEBUG " + b);
            badgesAndStadiums.put(b.getId(), stadiumFacade.findById(b.getStadiumId()));
        }
        model.addAttribute("badges", badges);
        model.addAttribute("stadiumsMap", badgesAndStadiums);
        
        if(request.isUserInRole("ROLE_ADMIN")) {
            return "/menu/badge/badgeList";
        }
        
        Map<StadiumDTO, TrainerDTO> stadiumsAndTrainers = new HashMap<>();
        
        for(BadgeDTO b: badges) {
            StadiumDTO stad = stadiumFacade.findById(b.getStadiumId());
            stadiumsAndTrainers.put(stad, trainerFacade.findTrainerById(stad.getStadiumLeaderId()));
        }
        model.addAttribute("stadiumsAndTrainers", stadiumsAndTrainers);
        return "/menu/badge/userBadgeList";
    }
    
    @RequestMapping(value = "/badge/new", method = RequestMethod.GET)
    public String newBadge(Model model, RedirectAttributes redirectAttrs) {
        model.addAttribute("newBadge", new BadgeDTO());
        System.out.println("DEBUG badge new called");
        return "/menu/badge/newBadge";
    }
    
    @ModelAttribute("stadiums") 
    public Collection<StadiumDTO> stadiums() {
        return stadiumFacade.findAll();
    }
    
    @ModelAttribute("trainers") 
    public Collection<TrainerDTO> trainers() {
        return trainerFacade.findAllTrainers();
    }
    
    @RequestMapping(value = "/badge/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("newBadge") BadgeDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        Long id;
        try{
            id = badgeFacade.assignBadge(formBean);
            System.out.println("DEBUG " + formBean.getId() + " " + formBean.getStadiumId());
            redirectAttributes.addFlashAttribute("alert_success", "Badge " + id + " was created");
        }catch(PokemonServiceException p) {
            redirectAttributes.addFlashAttribute("errorMessage", p.getMessage());
            return "redirect:/menu/badge/new";
        }
        return "redirect:" + uriBuilder.path("/menu/badge/view/{id}").buildAndExpand(id).encode().toUriString();
    }
    
    @RequestMapping(value = "/badge/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        model.addAttribute("badge", badgeFacade.findBadgeById(id));
        StadiumDTO stadium = stadiumFacade.findById(badgeFacade.findBadgeById(id).getStadiumId());
        model.addAttribute("stadium", stadium);
        return "/menu/badge/view";
    }
    
    @RequestMapping(value = "/badge/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable long id, Model model, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        BadgeDTO badge = badgeFacade.findBadgeById(id);
        badgeFacade.removeBadge(badge);
        redirectAttributes.addFlashAttribute("alert_success", "Badge was deleted.");
        return "redirect:" + uriBuilder.path("/menu/badge/badgeList").toUriString();
    }
    
    @ModelAttribute("userName")
    public String userName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return name;
    }
    
    @RequestMapping(value = "/badge/withtype")
    public String withType(@ModelAttribute("stadium") StadiumDTO stadiumDTO, Model model,
            UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        
        Collection<StadiumDTO> findByType = stadiumFacade.findByType(stadiumDTO.getType());
        if(findByType.isEmpty()) {
            findByType = stadiumFacade.findAll();
            model.addAttribute("alert_warning", "Nothing found, showing all records.");
        }
        
        Map<StadiumDTO, TrainerDTO> stadiumsAndTrainers = new HashMap<>();
        
        for(StadiumDTO f: findByType) {
            stadiumsAndTrainers.put(f, trainerFacade.findTrainerById(f.getStadiumLeaderId()));
        }
        model.addAttribute("stadiumsAndTrainers", stadiumsAndTrainers);
        return "/menu/badge/userBadgeList";
    }
    
}
