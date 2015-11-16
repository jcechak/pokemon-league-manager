package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;

import java.util.List;

/**
 * Interface describing the contract of a entity managing DAO.
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

    /**
     * Finds all pokemons.
     * @return collection with the all pokemons
     */
    List<Pokemon> findAll();

    /**
     * Retrieves a list of pokemons with chosen trainer.
     * @param trainer trainer who is teaching pokemons
     * @return collection of pokemons with selected trainer
     */
    List<Pokemon> findAllWithTrainer(Trainer trainer);

}
