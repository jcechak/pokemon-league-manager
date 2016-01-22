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

    /**
     * Creates a new pokemon with the given fields. USAGE: ./rest_tester.sh
     * http://localhost:8080/pa165/rest/pokemons/create -H "Content-Type:
     * application/json" --data
     * '{"name":"SomeName","nickname":"SomeNick","type":"BUG","skillLevel":"5","trainerId":"2"}'
     *
     * @param pokemon pokemon to be created
     * @param bindingResult binding result of validation
     * @return newly created pokemon with id
     */
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

    /**
     * Changes skill of a pokmeon. USAGE: ./rest_tester.sh
     * http://localhost:8080/pa165/rest/pokemons/changeskill/2 -H "Content-Type:
     * application/json" --data '20'
     *
     * @param id id of pokemon that will be changed
     * @param newSkill new skill of pokemon
     * @return pokemon with the changed skill
     */
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

    /**
     * Changes trainer of the given pokemon. USAGE: ./rest_tester.sh
     * http://localhost:8080/pa165/rest/pokemons/changetrainer/2 -H
     * "Content-Type: application/json" --data '3'
     *
     * @param id id of pokemon that will be changed
     * @param newTrainerId id of the new trainer
     * @return pokemon with the changed trainer
     */
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

    /**
     * Trades the two given pokemons. USAGE: ./rest_tester.sh
     * http://localhost:8080/pa165/rest/pokemons/trade -H "Content-Type:
     * application/json" --data '[2,3]'
     *
     * @param pokemonsIds list of two pokemon ids to be traded.
     */
    @RequestMapping(
            value = "/trade",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void tradePokemons(@RequestBody List<Long> pokemonsIds) {
        pokemonFacade.tradePokemon(pokemonsIds.get(0), pokemonsIds.get(1));
    }

    /**
     * Deletes the pokemon with given id. USAGE: ./rest_tester.sh -X DELETE
     * http://localhost:8080/pa165/rest/pokemons/1
     *
     * @param id id of pokemon to be deleted
     */
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

    /**
     * Returns pokemon with given id. USAGE: ./rest_tester.sh
     * http://localhost:8080/pa165/rest/pokemons/1
     *
     * @param id id of pokemon
     * @return pokemon with given id
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PokemonDTO getPokemon(@PathVariable("id") Long id) {
        try {
            PokemonDTO found = pokemonFacade.getPokemonById(id);
            if (found == null) {
                throw new ResourceNotFound();
            }
            return found;
        } catch (Exception ex) {
            throw new ResourceNotFound();
        }
    }

    /**
     * Gets all pokemons in the system. USAGE: ./rest_tester.sh
     * http://localhost:8080/pa165/rest/pokemons
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

    /**
     * Gets pokemons with the given trainer. USAGE: ./rest_tester.sh
     * http://localhost:8080/pa165/rest/pokemons/withtrainer -H "Content-Type:
     * application/json" --data '3'
     *
     * @param trainerId id of a trainer
     * @return pokemons that are traind by given trainer
     */
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

    /**
     * Gets pokemons with the given name. USAGE: ./rest_tester.sh
     * http://localhost:8080/pa165/rest/pokemons/withname -H "Content-Type:
     * application/json" --data 'Onix'
     *
     * @param name name of the pokemons
     * @return collection of pokemons with the given name
     */
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

    /**
     * Gets pokemons with the given type. USAGE: ./rest_tester.sh
     * http://localhost:8080/pa165/rest/pokemons/withtype -H "Content-Type:
     * application/json" --data '"ROCK"'
     *
     * @param type type of the returned pokemon(s)
     * @return collection of pokemons with given type. Might be empty but never
     * null.
     */
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
