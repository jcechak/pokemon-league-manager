package cz.muni.fi.pa165.pokemon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 *
 * @author Milos Bartak
 */
@Controller
@RequestMapping("/")
public class DefaultController {
    
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String login(ModelMap map) {
       System.out.println("default login controller called");
       return "login";
   }
   
   @RequestMapping(value = "/logout", method = RequestMethod.GET)
   public String logout(ModelMap map) {
       System.out.println("default logout controller called");
       return "logout";
   }

   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String index(ModelMap map) {
       System.out.println("default controller called");
       return "redirect:/menu/menu";
   }
}
