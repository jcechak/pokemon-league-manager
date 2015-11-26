package cz.muni.fi.pa165.pokemon.facade;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import java.util.Collection;

/**
 * Interface for facade layer for manipulating with trainer data transfer objects
 * 
 * @author Milos Bartak
 */
public interface TrainerFacade {
    
    /**
     * Saves the trainer to the system TODO: parameter
     * @param trainer the trainer to be saved
     */
    void createTrainer(TrainerDTO trainerDTO);
    
    /**
     * Deletes the given trainer from the system
     * @param trainer the trainer to be deleted
     */
    void deleteTrainer(TrainerDTO trainerDTO);
    
    /**
     * Updates the trainers info
     * @param trainer the trainer to be updated
     */
    void updateTrainer(TrainerDTO trainerDTO);
    
    /**
     * Checks whether the trainer is leader at given stadium
     * @param stadium the stadium we check the leader at
     * @return true if the trainer is leader of given stadium, false otherwise
     */
    boolean isLeaderOfTheStadium(TrainerDTO trainerDTO, StadiumDTO stadiumDTO);
    
    /**
     * Adds the pokemon to the trainer
     * @param pokemon the pokemon to be added
     */
    void addPokemon(TrainerDTO trainerDTO, PokemonDTO pokemonDTO);
    
    /**
     * Removes the pokemon from the trainer
     * @param trainerDTO owner of the pokemon
     * @param pokemon the pokemon that has to be removed
     */
    void removePokemon(TrainerDTO trainerDTO, PokemonDTO pokemonDTO);
    
    /**
     * Adds the badge to the trainer
     * @param badge the badge to be added
     */
    void addBadge(TrainerDTO trainerDTO, BadgeDTO badgeDTO);
    
    /**
     * Finds the trainer with given id
     * @param id the id of a trainer
     * @return trainer with given id
     */
    TrainerDTO findTrainerById(Long id);
    
    /**
     * Finds all trainers registered in the system
     * @return collection of all trainers
     */
    Collection<TrainerDTO> findAllTrainers();
           
    /**
     * Finds all trainer who own given pokemon
     * @param pokemon the pokemon we use as filter
     * @return collection of trainers who own given pokemon
     */
    Collection<TrainerDTO> findAllTrainersWithPokemon(PokemonDTO pokemonDTO);
    
    /**
     * Finds all trainer who own given badge
     * @param badge the badge we use as filter
     * @return collection of trainers who own given badge
     */
    Collection<TrainerDTO> findAllTrainersWithBadge(BadgeDTO badgeDTO);
    
    /**
     * Finds all trainers with given name
     * @param name the name of trainers we use as filter
     * @return collection of trainers with given name
     */
    Collection<TrainerDTO> findAllTrainersWithName(String nameDTO);
    
    /**
     * Finds all trainers with given surname
     * @param surname the surname of trainers we use as filter
     * @return collection of trainers with given surname
     */
    Collection<TrainerDTO> findAllTrainersWithSurname(String surname);
}
