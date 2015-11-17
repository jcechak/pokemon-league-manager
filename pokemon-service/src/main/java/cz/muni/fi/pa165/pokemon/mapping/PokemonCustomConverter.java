package cz.muni.fi.pa165.pokemon.mapping;

import cz.muni.fi.pa165.pokemon.entity.Trainer;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * Custom convertor between pokemon entity and pokemon DTO. Reason for this is
 * that pokemon DTO stores only trainer id and not a whole trainer to prevent
 * infinite recursion of dependencies and database fetches. It transforms
 * Trainer to Long (his/her id) and vice versa.
 *
 * @author Jaroslav Cechak
 */
public class PokemonCustomConverter implements CustomConverter {

    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {
        if (source instanceof Long) {
            //return TrainerService.getTrainerById(source);
            Trainer t = new Trainer();
            t.setId((Long) source);
            return t;
        } else if (source instanceof Trainer) {
            return ((Trainer) source).getId();
        } else {
            throw new MappingException("Unable to map a value " + source + " of type " + sourceClass + " to a value of type " + destinationClass);
        }
    }
}
