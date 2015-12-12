package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
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

/**
 *
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
    
    @RequestMapping(value = "/badge/badgeList")
    public String showList(Model model) {
        Collection<BadgeDTO> badges = badgeFacade.getAllBadges();
        Map<Long, StadiumDTO> badgesAndStadiums = new HashMap<>();
        for(BadgeDTO b:badges) {
            System.out.println("DEBUG " + b);
            badgesAndStadiums.put(b.getId(), stadiumFacade.findById(b.getStadiumId()));
        }
        model.addAttribute("badges", badges);
        model.addAttribute("stadiumsMap", badgesAndStadiums);
        return "/menu/badge/badgeList";
    }
    
    @RequestMapping(value = "/badge/new", method = RequestMethod.GET)
    public String newProduct(Model model) {
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
    public String create(@ModelAttribute("newBadge") BadgeDTO formBean, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder) {
        Long id = badgeFacade.assignBadge(formBean);
        System.out.println("DEBUG " + formBean.getId() + " " + formBean.getStadiumId());
        redirectAttributes.addFlashAttribute("alert_success", "Product " + id + " was created");
        return "redirect:" + uriBuilder.path("/menu/badge/view/{id}").buildAndExpand(id).encode().toUriString();
    }
    
    @RequestMapping(value = "/badge/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model) {
        model.addAttribute("badge", badgeFacade.findBadgeById(id));
        StadiumDTO stadium = stadiumFacade.findById(badgeFacade.findBadgeById(id).getStadiumId());
        model.addAttribute("stadium", stadium);
        return "/menu/badge/view";
    }
        
}
