package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.exceptions.PokemonServiceException;
import cz.muni.fi.pa165.pokemon.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.pokemon.rest.exceptions.ResourceNotFound;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


/**
 * Controller for handling exceptions.
 * @author Marek Sabo
 */

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler({PokemonServiceException.class, ConstraintViolationException.class, NullPointerException.class,
    IllegalArgumentException.class, NonTransientDataAccessResourceException.class, DataIntegrityViolationException.class,
            InvalidDataAccessApiUsageException.class, ResourceNotFound.class, InvalidParameterException.class})
    public ModelAndView handleCustomException(Exception ex) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let the framework handle it -
        // like the OrderNotFoundException example at the start of this post.
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }
        ModelAndView model = new ModelAndView("error/generic_error");
        model.addObject("errorMessage", ex.getMessage());
        model.addObject("exceptionName", ClassUtils.getShortName(ex.getClass()));

        System.out.println("handleCustomException occurred: " + ex.getMessage());

        return model;

    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception ex) {

        ModelAndView model = new ModelAndView("error/generic_error");
        model.addObject("errorMessage", "An error occurred. Please try again later.");
        model.addObject("exceptionName", ClassUtils.getShortName(ex.getClass()));

        System.out.println("handleAllException occurred: " + ex.getMessage());
        return model;
    }

    @ModelAttribute("userName")
    public String userName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return name;
    }

}
