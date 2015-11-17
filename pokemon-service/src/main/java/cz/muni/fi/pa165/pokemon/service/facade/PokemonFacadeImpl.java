package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public PokemonDTO createPokemon(PokemonCreateDTO pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        Pokemon newPokemon = mappingService.map(pokemon, Pokemon.class);
        pokemonService.createPokemon(newPokemon);
        return mappingService.map(newPokemon, PokemonDTO.class);
    }

    @Override
    public PokemonDTO getPokemonById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return mappingService.map(pokemonService.getPokemonById(id), PokemonDTO.class);
    }

    @Override
    public void changeSkill(PokemonDTO pokemon, int newSkill) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        if (newSkill < 0) {
            throw new IllegalArgumentException("Skill cannot be negative number");
        }
        pokemon.setSkillLevel(newSkill);
        pokemonService.updatePokemon(mappingService.map(pokemon, Pokemon.class));
    }

    @Override
    public void changeTrainer(PokemonDTO pokemon, TrainerDTO newTrainer) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        if (newTrainer == null) {
            throw new IllegalArgumentException("Trainer cannot be null.");
        }
        pokemon.setTrainerId(newTrainer.getId());
        pokemonService.updatePokemon(mappingService.map(pokemon, Pokemon.class));
    }

    @Override
    public void deletePokemon(PokemonDTO pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        pokemonService.deletePokemon(mappingService.map(pokemon, Pokemon.class));
    }

    @Override
    public List<PokemonDTO> getAllPokemons() {
        return mappingService.map(pokemonService.getAllPokemons(), PokemonDTO.class);
    }

    @Override
    public List<PokemonDTO> getAllPokemonsOfTrainerWithId(Long trainerId) {
        if (trainerId == null) {
            throw new IllegalArgumentException("Trainer id cannot be null.");
        }
        return mappingService.map(trainerService.findTrainerById(trainerId).getPokemons(), PokemonDTO.class);
    }

    @Override
    public List<PokemonDTO> getAllPokemonsWithName(String name) {
        List<Pokemon> allPokemons = pokemonService.getAllPokemons();
        List<PokemonDTO> pokemonsWithName = new LinkedList<>();
        for (Pokemon p : allPokemons) {
            if (p.getName().equals(name)) {
                pokemonsWithName.add(mappingService.map(p, PokemonDTO.class));
            }
        }
        return pokemonsWithName;
    }

    @Override
    public List<PokemonDTO> getAllPokemonsWtihType(PokemonType type) {
        List<Pokemon> allPokemons = pokemonService.getAllPokemons();
        List<PokemonDTO> pokemonsWithType = new LinkedList<>();
        for (Pokemon p : allPokemons) {
            if (p.getType().equals(type)) {
                pokemonsWithType.add(mappingService.map(p, PokemonDTO.class));
            }
        }
        return pokemonsWithType;
    }    
}
