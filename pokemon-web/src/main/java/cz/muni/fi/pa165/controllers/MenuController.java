package cz.muni.fi.pa165.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Milos Bartak
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    
    @RequestMapping(value = "/menu")
    public String showMenu() {
        return "/menu/menu";
    }
}
