package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import java.util.List;

/**
 * Service interface defining service's contract with facade
 *
 * @author Jaroslav Cechak
 */
public interface PokemonService {

    /**
     * Creates the pokemon in the database.
     *
     * @param pokemon the pokemon to be persisted
     */
    void createPokemon(Pokemon pokemon);

    /**
     * Updates the pokemon record in the database.
     *
     * @param pokemon the pokemon with new attribute values
     */
    void updatePokemon(Pokemon pokemon);

    /**
     * Deletes the pokemon from the database.
     *
     * @param pokemon the pokemon to be deleted
     */
    void deletePokemon(Pokemon pokemon);

    /**
     * Gets pokemon with given id.
     *
     * @param id the id of the pokemon
     * @return the pokemon with the given id
     */
    Pokemon getPokemonById(Long id);

    /**
     * Retrieves all the pokemons from the database.
     *
     * @return List of all pokemons currently in the database
     */
    List<Pokemon> getAllPokemons();

}
