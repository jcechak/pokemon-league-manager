package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import java.util.Collection;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Implementation of facade interface defining the facade's contracts.
 *
 * @author Jaroslav Cechak
 */
@Service
@Transactional
public class PokemonFacadeImpl implements PokemonFacade {

    @Inject
    private PokemonService pokemonService;

    @Inject
    private MappingService mappingService;

    @Inject
    private TrainerService trainerService;

    @Override
    public Long createPokemon(PokemonCreateDTO pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        Pokemon newPokemon = mappingService.map(pokemon, Pokemon.class);
        return pokemonService.createPokemon(newPokemon);
    }

    @Override
    public PokemonDTO getPokemonById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return mappingService.map(
                pokemonService.getPokemonById(id),
                PokemonDTO.class
        );
    }

    @Override
    public void changeSkill(Long pokemonId, int newSkill) {
        if (pokemonId == null) {
            throw new IllegalArgumentException("Pokemon's id cannot be null.");
        }
        if (newSkill < 0) {
            throw new IllegalArgumentException("Skill cannot be negative number");
        }
        pokemonService.changeSkill(
                pokemonService.getPokemonById(pokemonId),
                newSkill
        );
    }

    @Override
    public void changeTrainer(Long pokemonId, Long newTrainerId) {
        if (pokemonId == null) {
            throw new IllegalArgumentException("Pokemon's id cannot be null.");
        }
        if (newTrainerId == null) {
            throw new IllegalArgumentException("Trainer's id cannot be null.");
        }
        pokemonService.changeTrainer(
                pokemonService.getPokemonById(pokemonId),
                trainerService.findTrainerById(newTrainerId)
        );
    }

    @Override
    public void tradePokemon(Long pokemonId1, Long pokemonId2) {
        pokemonService.tradePokemon(
                pokemonService.getPokemonById(pokemonId1),
                pokemonService.getPokemonById(pokemonId2)
        );

    }

    @Override
    public void deletePokemon(Long pokemonId) {
        if (pokemonId == null) {
            throw new IllegalArgumentException("Pokemon's id cannot be null.");
        }
        pokemonService.deletePokemon(pokemonService.getPokemonById(pokemonId));
    }

    @Override
    public Collection<PokemonDTO> getAllPokemons() {
        return mappingService.map(
                pokemonService.getAllPokemons(),
                PokemonDTO.class
        );
    }

    @Override
    public Collection<PokemonDTO> getAllPokemonsOfTrainerWithId(Long trainerId) {
        if (trainerId == null) {
            throw new IllegalArgumentException("Trainer's id cannot be null.");
        }
        return mappingService.map(
                pokemonService.getAllPokemonsOfTrainer(
                        trainerService.findTrainerById(trainerId)
                ),
                PokemonDTO.class
        );
    }

    @Override
    public Collection<PokemonDTO> getAllPokemonsWithName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Pokemon's name cannot be null.");
        }
        return mappingService.map(
                pokemonService.getAllPokemonsWithName(name),
                PokemonDTO.class
        );
    }

    @Override
    public Collection<PokemonDTO> getAllPokemonsWithType(PokemonType type) {
        if (type == null) {
            throw new IllegalArgumentException("Pokemon's type cannot be null.");
        }
        return mappingService.map(
                pokemonService.getAllPokemonsWithType(type),
                PokemonDTO.class
        );
    }
}
