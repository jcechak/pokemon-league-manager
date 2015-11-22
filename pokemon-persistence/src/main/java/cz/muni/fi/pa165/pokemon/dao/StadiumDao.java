package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.List;

/**
 * Interface for Stadium data acces object having basic CRUD operations.
 * 
 * @author Milos Bartak
 */
public interface StadiumDao {
    /**
     * Searches for the stadium with given id
     * @param id trainers id
     * @return the stadium with id equal to param id or null if no stadium with that id exists
     */
    Stadium findById(Long id);
    
    /**
     * Creates new stadium
     * @param stadium the stadium that has to be created
     */
    void create(Stadium stadium);
    
    /**
     * Updates the stadium
     * @param stadium stadium that has to be updated
     */
    void update(Stadium stadium);
    
    /**
     * Deletes a stadium
     * @param stadium the stadium that has to be deleted
     */
    void delete(Stadium stadium);
    
    /**
     * Searches for all stadiums
     * @return the list of stadiums
     */
    List<Stadium> findAll();
    
    /**
     * Searches for the stadium placed in the city
     * @param city name of the city
     * @return stadium that is in the city
     */
    Stadium findByCity(String city);
    
    /**
     * Searches for the stadium with given leader trainer
     * @param leader the stadium leader
     * @return stadium that with given leader trainer
     */
    Stadium findByStadiumLeader(Trainer leader);
    
    /**
     * Searches for the stadium with given pokemon type
     * @param type the type of leaders trainer pokemon
     * @return stadium list with given pokemon type
     */
    List<Stadium> findByPokemonType(PokemonType type);
}
