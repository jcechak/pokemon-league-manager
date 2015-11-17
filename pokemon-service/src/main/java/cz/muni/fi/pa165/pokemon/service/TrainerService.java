package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Milos Bartak
 */
@Service
public interface TrainerService {
    
    /**
     * Creates the trainer
     * @param trainer the trainer to be created
     */
    void createTrainer(Trainer trainer);
    
    /**
     * Deletes the trainer
     * @param trainer the trainer to be deleted
     */
    void deleteTrainer(Trainer trainer);
    
    /**
     * Updates the trainer
     * @param trainer the trainer to be updated
     */
    void updateTrainer(Trainer trainer);
    
    /**
     * Checks whether the trainer is leader at given stadium
     * @param stadium the stadium we check the leader at
     * @return true if the trainer is leader of given stadium, false otherwise
     */
    boolean isLeaderOfTheStadium(Stadium stadium);
    
    /**
     * Adds the pokemon to the trainer
     * @param pokemon the pokemon to be added
     */
    void addPokemon(Pokemon pokemon);
    
    /**
     * Adds the badge to the trainer
     * @param badge the badge to be added
     */
    void addBadge(Badge badge);
    
    /**
     * Finds a trainer with given id
     * @param id the id to search by
     * @return the trainer with given id
     */
    Trainer findTrainerById(Long id);
    
    /**
     * Finds all trainers
     * @return list of all trainers
     */
    List<Trainer> findAllTrainers();
    
    /**
     * Finds all trainer who own given pokemon
     * @param pokemon the pokemon we use as filter
     * @return collection of trainers who own given pokemon
     */
    Collection<Trainer> findAllTrainersWithPokemon(Pokemon pokemon);
    
    /**
     * Finds all trainer who own given badge
     * @param badge the badge we use as filter
     * @return collection of trainers who own given badge
     */
    Collection<Trainer> findAllTrainersWithBadge(Badge badge);
    
    /**
     * Finds all trainers with given name
     * @param name the name of trainers we use as filter
     * @return collection of trainers with given name
     */
    Collection<Trainer> findAllTrainersWithName(String name);
    
    /**
     * Finds all trainers with given surname
     * @param surname the surname of trainers we use as filter
     * @return collection of trainers with given surname
     */
    Collection<Trainer> findAllTrainersWithSurname(String surname);
}
