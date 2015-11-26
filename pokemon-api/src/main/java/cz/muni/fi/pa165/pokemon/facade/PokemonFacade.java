package cz.muni.fi.pa165.pokemon.facade;

import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.List;

/**
 * Facade interface defining the facade's contracts with outer world.
 *
 * @author Jaroslav Cechak
 */
public interface PokemonFacade {

    /**
     * Save the given pokemon into system.
     *
     * @param pokemon pokemon to be saved
     * @return id of newly created pokemon
     */
    Long createPokemon(PokemonCreateDTO pokemon);

    /**
     * Returns pokemon with given id
     *
     * @param id id of pokemon
     * @return pokemon with given id
     */
    PokemonDTO getPokemonById(Long id);

    /**
     * Changes the skill of the pokemon
     *
     * @param pokemonId id of pokemon to be updated
     * @param newSkill new skill level of the pokemon
     */
    void changeSkill(Long pokemonId, int newSkill);

    /**
     * Changes the pokemon's trainer (owner)
     *
     * @param pokemonId id of pokemon to be updated
     * @param newTrainerId id of pokemon's new trainer
     */
    void changeTrainer(Long pokemonId, Long newTrainerId);
    
    /**
     * Trades pokemons by switching their trainers.
     * @param pokemonId1 id of first pokemon to trade
     * @param pokemonId2 id of second pokemon to trade
     */
    void tradePokemon(Long pokemonId1, Long pokemonId2);

    /**
     * Deletes the given pokemon from system.
     *
     * @param pokemonId id of pokemon to be deleted
     */
    void deletePokemon(Long pokemonId);

    /**
     * Returns {@link java.util.List List} of all pokemons present in the
     * systems.
     *
     * @return {@link java.util.List List} of all pokemons present in the
     * systems
     */
    List<PokemonDTO> getAllPokemons();

    /**
     * Returns {@link java.util.List List} of all pokemons that are being
     * trained by the given trainer in the system.
     *
     * @param trainerId the id of common trainer of the pokemons
     * @return {@link java.util.List List} of all pokemons that are being
     * trained by the given trainer
     */
    List<PokemonDTO> getAllPokemonsOfTrainerWithId(Long trainerId);

    /**
     * Returns {@link java.util.List List} of all pokemons that have the given
     * name in the system.
     *
     * @param name name of pokemon
     * @return {@link java.util.List List} of all pokemons that have the given
     * name
     */
    List<PokemonDTO> getAllPokemonsWithName(String name);

    /**
     * Returns {@link java.util.List List} of all pokemons of the given type in
     * the system.
     *
     * @param type type of pokemon
     * @return {@link java.util.List List} of all pokemons of the given type
     */
    List<PokemonDTO> getAllPokemonsWithType(PokemonType type);
}
