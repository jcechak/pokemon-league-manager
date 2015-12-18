package cz.muni.fi.pa165.pokemon.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for default menu.
 * @author Milos Bartak
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @RequestMapping(value = "/menu")
    public String showMenu(ModelMap map) {
        return "/menu/menu";
    }
    
    @ModelAttribute("userName")
    public String userName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return name;
    }
}
