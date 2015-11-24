package cz.muni.fi.pa165.exceptions;

/**
 * Class representing exception for service layer
 * 
 * @author Milos Bartak
 */
public class PokemonServiceException extends RuntimeException{
    public PokemonServiceException() {
        super();
    }

    public PokemonServiceException(String message) {
        super(message);
    }

    public PokemonServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PokemonServiceException(Throwable cause) {
        super(cause);
    }

    public PokemonServiceException(String message, Throwable cause, 
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
