package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
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
     * @return returns id of a newly created pokemon
     */
    Long createPokemon(Pokemon pokemon);

    /**
     * Updates the pokemon record in the database.
     *
     * @param pokemon the pokemon with new attribute values
     */
    void updatePokemon(Pokemon pokemon);

    /**
     * Changes the skill of the pokemon
     *
     * @param pokemon pokemon to be updated
     * @param newSkill new skill level of the pokemon
     */
    void changeSkill(Pokemon pokemon, int newSkill);

    /**
     * Changes the pokemon's trainer (owner)
     *
     * @param pokemon pokemon to be updated
     * @param newTrainer pokemon's new trainer
     */
    void changeTrainer(Pokemon pokemon, Trainer newTrainer);

    /**
     * Trades pokemons by switching their trainers.
     *
     * @param pokemon1 first pokemon to trade
     * @param pokemon2 second pokemon to trade
     */
    void tradePokemon(Pokemon pokemon1, Pokemon pokemon2);

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

    /**
     * Returns {@link java.util.List List} of all pokemons that are being
     * trained by the given trainer in the system.
     *
     * @param trainer the common trainer of the pokemons
     * @return {@link java.util.List List} of all pokemons that are being
     * trained by the given trainer
     */
    List<Pokemon> getAllPokemonsOfTrainer(Trainer trainer);

    /**
     * Returns {@link java.util.List List} of all pokemons that have the given
     * name in the system.
     *
     * @param name name of pokemon
     * @return {@link java.util.List List} of all pokemons that have the given
     * name
     */
    List<Pokemon> getAllPokemonsWithName(String name);

    /**
     * Returns {@link java.util.List List} of all pokemons of the given type in
     * the system.
     *
     * @param type type of pokemon
     * @return {@link java.util.List List} of all pokemons of the given type
     */
    List<Pokemon> getAllPokemonsWithType(PokemonType type);
}
