package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.entity.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Interface of TrainerService providing service to facade layer.
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
     * @param trainer the trainer whom we check is leader of the stadium
     * @param stadium the stadium we check the leader at
     * @return true if the trainer is leader of given stadium, false otherwise
     */
    boolean isLeaderOfTheStadium(Trainer trainer, Stadium stadium);
    
    /**
     * Checks if the trainer is ready to participate in a tournament
     * @param trainer the trainer that want to enroll
     * @param tournament the tournament the trainer wants to enroll to
     * @return true if trainer may participate, false otherwise
     */
    boolean mayEnrollInTournament(Trainer trainer, Tournament tournament);
    
    /**
     * Adds the pokemon to the trainer
     * @param trainer the trainer to add the pokemon to
     * @param pokemon the pokemon to be added
     */
    void addPokemon(Trainer trainer, Pokemon pokemon);
    
    /**
     * Removes the pokemon from the trainer
     * @param trainer te owner of the pokemon
     * @param pokemon the pokemon that has to be removed
     */
    void removePokemon(Trainer trainer, Pokemon pokemon);
    
    /**
     * Adds the badge to the trainer
     * @param trainer the trainer to add the badge to
     * @param badge the badge to be added
     */
    void addBadge(Trainer trainer, Badge badge);
    
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
