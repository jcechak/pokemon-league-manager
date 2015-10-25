package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Pokemon;

/**
 * @author Marek Sabo
 */
public interface PokemonDao {

    /**
     * Creates a Pokemon entity in the database.
     * @param pokemon entity to be created
     */
    void create(Pokemon pokemon);

    /**
     * Updates values of entity in the database.
     * @param pokemon entity to be changed
     */
    void update(Pokemon pokemon);

    /**
     * Deletes entity in the database.
     * @param pokemon entity to be deleted
     */
    void delete(Pokemon pokemon);

    /**
     * Finds entity with the given id.
     * @param id of entity to be retrieved
     * @return Pokemon with given id.
     */
    Pokemon findById(Long id);

}
