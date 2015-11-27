package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Tournament;

/**
 * Interface representing Tournament Dao
 * 
 * @author Milos Bartak
 */
public interface TournamentDao {
    /**
     * Creates a Tournament entry in the database.
     * @param tournament Tournament object that will be created
     */
    void create(Tournament tournament);

    /**
     * Updates a Tournament entry in the database.
     * @param tournament Tournament object that will be updated
     */
    void update(Tournament tournament);

    /**
     * Deletes a Tournament entry from the database.
     * @param tournament Tournament object that will be deleted
     */
    void delete(Tournament tournament);

    /**
     * Finds a Tournament entity with given id in the database
     * @param id ID of the Tournament to be found
     * @return Tournament entity with given id
     */
    Tournament findById(Long id);
}
