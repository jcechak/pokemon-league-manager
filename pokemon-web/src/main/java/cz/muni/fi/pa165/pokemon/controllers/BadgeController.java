package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    
    public void setBadgeFacade(BadgeFacade badgeFacade) {
        this.badgeFacade = badgeFacade;
    }
    
    @RequestMapping(value = "/badgeList")
    public String showList(Model model) {
        Collection<BadgeDTO> badges = badgeFacade.getAllBadges();
        Map<Long, StadiumDTO> badgesAndStadiums = new HashMap<>();
        for(BadgeDTO b:badges) {
            System.out.println("DEBUG " + b);
            badgesAndStadiums.put(b.getId(), stadiumFacade.findById(b.getId()));
        }
        model.addAttribute("badges", badges);
        model.addAttribute("stadiumsMap", badgesAndStadiums);
        
        
        
        return "/menu/badgeList";
    }
}
