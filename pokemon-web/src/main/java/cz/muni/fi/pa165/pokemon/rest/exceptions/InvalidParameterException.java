package cz.muni.fi.pa165.pokemon.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that is to be thrown in case there is any problem with supplied
 * parameters.
 *
 * @author Jaroslav Cechak
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid parameters.")
public class InvalidParameterException extends RuntimeException {

}
