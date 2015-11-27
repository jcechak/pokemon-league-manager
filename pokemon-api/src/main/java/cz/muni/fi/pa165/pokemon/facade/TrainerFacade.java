package cz.muni.fi.pa165.pokemon.facade;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TournamentDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import java.util.Collection;

/**
 * Interface for facade layer for manipulating with trainer data transfer objects.
 * 
 * @author Milos Bartak
 */
public interface TrainerFacade {
    
    /**
     * Saves the trainer to the system
     * @param trainerDTO the trainer to be saved
     */
    void createTrainer(TrainerDTO trainerDTO);
    
    /**
     * Deletes the given trainer from the system
     * @param trainerDTO the trainer to be deleted
     */
    void deleteTrainer(TrainerDTO trainerDTO);
    
    /**
     * Updates the trainers info
     * @param trainerDTO the trainer to be updated
     */
    void updateTrainer(TrainerDTO trainerDTO);
    
    /**
     * Checks whether the trainer is leader at given stadium
     * @param trainerDTO the trainer to check if is leader
     * @param stadiumDTO the stadium we check the leader at
     * @return true if the trainer is leader of given stadium, false otherwise
     */
    boolean isLeaderOfTheStadium(TrainerDTO trainerDTO, StadiumDTO stadiumDTO);
    
    /**
     * Checks whether trainer may enroll in tournament
     * @param trainerDTO trainer to enroll tournament
     * @param tournamentDTO the tournament to enroll in
     * @return true if trainer may enroll, false otherwise
     */
    boolean mayEnrollInTournament(TrainerDTO trainerDTO, TournamentDTO tournamentDTO);
    
    /**
     * Adds the pokemon to the trainer
     * @param pokemonDTO the pokemon to be added
     */
    void addPokemon(TrainerDTO trainerDTO, PokemonDTO pokemonDTO);
    
    /**
     * Removes the pokemon from the trainer
     * @param trainerDTO owner of the pokemon
     * @param pokemonDTO the pokemon that has to be removed
     */
    void removePokemon(TrainerDTO trainerDTO, PokemonDTO pokemonDTO);
    
    /**
     * Adds the badge to the trainer
     * @param trainerDTO the trainet to add badge to
     * @param badgeDTO the badge to be added
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
     * @param pokemonDTO the pokemon we use as filter
     * @return collection of trainers who own given pokemon
     */
    Collection<TrainerDTO> findAllTrainersWithPokemon(PokemonDTO pokemonDTO);
    
    /**
     * Finds all trainer who own given badge
     * @param badgeDTO the badge we use as filter
     * @return collection of trainers who own given badge
     */
    Collection<TrainerDTO> findAllTrainersWithBadge(BadgeDTO badgeDTO);
    
    /**
     * Finds all trainers with given name
     * @param name the name of trainers we use as filter
     * @return collection of trainers with given name
     */
    Collection<TrainerDTO> findAllTrainersWithName(String name);
    
    /**
     * Finds all trainers with given surname
     * @param surname the surname of trainers we use as filter
     * @return collection of trainers with given surname
     */
    Collection<TrainerDTO> findAllTrainersWithSurname(String surname);
}
