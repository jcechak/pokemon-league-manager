package cz.muni.fi.pa165.pokemon.controllers;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller for login page.
 * @author Milos Bartak
 */
@Controller
@RequestMapping("/")
public class DefaultController {
    
   @RequestMapping(value = "/login", method = RequestMethod.GET)
   public String login(HttpServletRequest request, ModelMap map) {
       System.out.println("default login controller called");
       return "login";
   }
   
   @RequestMapping(value = "/logout", method = RequestMethod.GET)
   public String logout(HttpServletRequest request, HttpServletResponse response) {
       System.out.println("default logout controller called");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
   }

   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String index(ModelMap map) {
       System.out.println("default controller called");
       return "redirect:/menu/menu";
   }

}
