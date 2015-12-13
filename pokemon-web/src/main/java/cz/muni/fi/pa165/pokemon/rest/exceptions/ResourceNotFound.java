package cz.muni.fi.pa165.pokemon.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is to be thrown in case there is no resource to be returned.
 * 
 * @author Jaroslav Cechak
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not such resource found.")
public class ResourceNotFound extends RuntimeException {

}
