package cz.muni.fi.pa165.pokemon.facade;

import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;

import java.util.Collection;

/**
 * Interface for facade layer for stadium data transfer objects manipulation
 * @author Dominika Talianova
 */
public interface StadiumFacade {

    /**
     * Saves the stadium to the system
     * @param stadium the stadium to be saved
     * @return staidum id
     */
    Long createStadium(StadiumDTO stadium);

    /**
     * updates the stadium in the system
     * @param stadium the stadium to be updated
     */
    void updateStadium(StadiumDTO stadium);

    /**
     * deletes the stadium from the system
     * @param stadium the stadium to be deleted
     */
    void deleteStadium(StadiumDTO stadium);

    /**
     * finds stadium by given ig
     * @param id the id used for searching for the right stadium
     * @return found stadium
     */
    StadiumDTO findById(Long id);

    /**
     * finds all stadiums
     * @return collection of found stadiums
     */
    Collection<StadiumDTO> findAll();

    /**
     * finds stadiums of give type
     * @param type the type of stadiums to be returned
     * @return found stadiums of certain type
     */
    Collection<StadiumDTO> findByType(PokemonType type);

    /**
     * finds stadium by given city
     * @param city the city that the stadium is search for
     * @return found stadium
     */
    StadiumDTO findByCity(String city);

    /**
     * finds stadium by given stadium leader
     * @param leader the leader of the required stadium
     * @return found stadium
     */
    StadiumDTO findStadiumByLeader(TrainerDTO leader);

    /**
     * Retrieves the leader of the stadium
     * @param stadiumDTO the stadium where the leader is looked for
     * @return the leader of given stadium
     */
    TrainerDTO getTheLeader(StadiumDTO stadiumDTO);

    /**
     * Checks whether the stadium has a leader
     * @param stadiumDTO stadium to be checked
     * @return true if the stadium has a leader, false otherwise
     */
    boolean hasLeader(StadiumDTO stadiumDTO);


}
