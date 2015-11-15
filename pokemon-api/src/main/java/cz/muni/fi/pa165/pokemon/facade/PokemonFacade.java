package cz.muni.fi.pa165.pokemon.facade;

import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.List;

/**
 * Facade interface defining the facade's contracts.
 *
 * @author Jaroslav Cechak
 */
public interface PokemonFacade {

    /**
     * Save the given pokemon into system.
     *
     * @param pokemon pokemon to be saved
     * @return id given to the pokemon upon saving
     */
    Long createPokemon(PokemonDTO pokemon);

    /**
     * Changes the skill of the pokemon
     *
     * @param newSkill new skill level of the pokemon
     */
    void changeSkill(Long pokemonId, int newSkill);

    /**
     * Changes the pokemon's trainer (owner)
     *
     * @param newTrainer pokemon's new trainer
     */
    void changeTrainer(TrainerDTO newTrainer);

    /**
     * Deletes the given pokemon from system.
     *
     * @param pokemon pokemon to be deleted
     */
    void deletePokemon(PokemonDTO pokemon);

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
     * @param trainer the common trainer of the pokemons
     * @return {@link java.util.List List} of all pokemons that are being
     * trained by the given trainer
     */
    List<PokemonDTO> getAllPokemonsOfTrainer(TrainerDTO trainer);

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
    List<PokemonDTO> getAllPokemonsWtihType(PokemonType type);
}
