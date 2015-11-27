package cz.muni.fi.pa165.pokemon.mapping;

import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.service.BadgeService;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
import cz.muni.fi.pa165.pokemon.service.StadiumService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import org.dozer.CustomConverter;
import org.dozer.MappingException;

import javax.inject.Inject;

/**
 * Custom converter between entities and their ids. Reason for this is that some
 * DTO's store only id's and not a whole entity to prevent infinite recursion of
 * dependencies and database fetches. It transforms Entity to Long (id) and vice
 * versa.
 *
 * @author Jaroslav Cechak
 */
public class EntityIdCustomConverter implements CustomConverter {

    @Inject
    private TrainerService trainerService;

    @Inject
    private BadgeService badgeService;

    @Inject
    private PokemonService PokemonService;

    @Inject
    private StadiumService stadiumService;

    @Override
    public Object convert(Object destination, Object source, Class<?> destinationClass, Class<?> sourceClass) {
        if (source instanceof Long) {
            if (destinationClass == Trainer.class) {
                return trainerService.findTrainerById((Long) source);
            } else if (destinationClass == Badge.class) {
                return badgeService.findBadgeById((Long) source);
            } else if (destinationClass == Pokemon.class) {
                return PokemonService.getPokemonById((Long) source);
            } else if (destinationClass == Stadium.class) {
                return stadiumService.getStadiumById((Long) source);
            } else {
                throw new MappingException("Unable to map a value " + source + " of type " + sourceClass + " to a value of type " + destinationClass);
            }
        } else if (source instanceof Trainer) {
            return ((Trainer) source).getId();
        } else if (source instanceof Badge) {
            return ((Badge) source).getId();
        } else if (source instanceof Pokemon) {
            return ((Pokemon) source).getId();
        } else if (source instanceof Stadium) {
            return ((Stadium) source).getId();
        } else {
            throw new MappingException("Unable to map a value " + source + " of type " + sourceClass + " to a value of type " + destinationClass);
        }
    }
}
