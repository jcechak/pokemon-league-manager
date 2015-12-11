package cz.muni.fi.pa165.controllers;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import java.util.Collection;
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
    
    /*@Autowired
    private BadgeFacade badgeFacade;
    
    public void setBadgeFacade(BadgeFacade badgeFacade) {
        this.badgeFacade = badgeFacade;
    }*/
    
    @RequestMapping(value = "/badgeList")
    public String showList(Model model) {
        
        /*Collection<BadgeDTO> badges = badgeFacade.getAllBadges();
        model.addAttribute("badges", badges);*/
        return "/menu/badgeList";
    }
}
