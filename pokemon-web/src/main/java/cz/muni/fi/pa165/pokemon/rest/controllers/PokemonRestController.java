package cz.muni.fi.pa165.pokemon.rest.controllers;

import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.rest.RestApiUris;
import cz.muni.fi.pa165.pokemon.rest.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.pokemon.rest.exceptions.ResourceNotFound;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller handles all the calls to REST API concerning operations with
 * pokemons.
 *
 * @author Jaroslav Cechak
 */
@RestController
@RequestMapping(path = RestApiUris.POKEMON_URI)
public class PokemonRestController {

    @Inject
    private PokemonFacade pokemonFacade;

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PokemonDTO createPokemon(@Valid @RequestBody PokemonCreateDTO pokemon, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new InvalidParameterException();
            }
            Long id = pokemonFacade.createPokemon(pokemon);
            return pokemonFacade.getPokemonById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(
            value = "/changeskill/{id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PokemonDTO changeSkill(@PathVariable("id") Long id, @RequestBody int newSkill) {
        try {
            pokemonFacade.changeSkill(id, newSkill);
            return pokemonFacade.getPokemonById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(
            value = "/changetrainer/{id}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PokemonDTO changeTrainer(@PathVariable("id") Long id, @RequestBody Long newTrainerId) {
        try {
            pokemonFacade.changeTrainer(id, newTrainerId);
            return pokemonFacade.getPokemonById(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(
            value = "/trade",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void tradePokemons(@RequestBody List<Long> pokemonsIds) {
        pokemonFacade.tradePokemon(pokemonsIds.get(0), pokemonsIds.get(1));
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE
    )
    public void deletePokemon(@PathVariable("id") Long id) {
        try {
            pokemonFacade.deletePokemon(id);
        } catch (Exception ex) {
            throw new InvalidParameterException();
        }
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PokemonDTO getPokemon(@PathVariable("id") Long id) {
        try {
            return pokemonFacade.getPokemonById(id);
        } catch (Exception ex) {
            throw new ResourceNotFound();
        }
    }

    /**
     * Gets all pokemons in the system.
     * curl -i -X GET http://localhost:8080/pa165/rest/pokemons
     * 
     * @return all pokemons in the system
     */
    @RequestMapping(
            value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Collection<PokemonDTO> getAllPokemons() {
        try {
            return pokemonFacade.getAllPokemons();
        } catch (Exception ex) {
            throw new ResourceNotFound();
        }
    }

    @RequestMapping(
            value = "/withtrainer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Collection<PokemonDTO> getPokemonOfTrainer(@RequestBody Long trainerId) {
        try {
            return pokemonFacade.getAllPokemonsOfTrainerWithId(trainerId);
        } catch (Exception ex) {
            throw new ResourceNotFound();
        }
    }

    @RequestMapping(
            value = "/withname",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Collection<PokemonDTO> getPokemonWithName(@RequestBody String name) {
        try {
            return pokemonFacade.getAllPokemonsWithName(name);
        } catch (Exception ex) {
            throw new ResourceNotFound();
        }
    }

    @RequestMapping(
            value = "/withtype",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Collection<PokemonDTO> getPokemonWithType(@RequestBody PokemonType type) {
        try {
            return pokemonFacade.getAllPokemonsWithType(type);
        } catch (Exception ex) {
            throw new ResourceNotFound();
        }
    }
}
