package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.PokemonDao;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of Pokemon service interface.
 *
 * @author Jaroslav Cechak
 */
@Service
public class PokemonServiceImpl implements PokemonService {

    @Inject
    private PokemonDao pokemonDao;

    @Inject
    private TrainerService trainerService;

    @Override
    public Long createPokemon(Pokemon pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        pokemonDao.create(pokemon);
        trainerService.addPokemon(pokemon.getTrainer(), pokemon);
        return pokemon.getId();
    }

    @Override
    public void updatePokemon(Pokemon pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        pokemonDao.update(pokemon);
    }

    @Override
    public void changeSkill(Pokemon pokemon, int newSkill) {
        pokemon.setSkillLevel(newSkill);
        pokemonDao.update(pokemon);
    }

    @Override
    public void changeTrainer(Pokemon pokemon, Trainer newTrainer) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        if (newTrainer == null) {
            throw new IllegalArgumentException("Trainer cannot be null.");
        }
        Trainer oldTrainer = pokemon.getTrainer();

        oldTrainer.removePokemon(pokemon);
        trainerService.updateTrainer(oldTrainer);

        newTrainer.addPokemon(pokemon);
        trainerService.updateTrainer(newTrainer);

        pokemon.setTrainer(newTrainer);
        pokemonDao.update(pokemon);
    }

    @Override
    public void tradePokemon(Pokemon pokemon1, Pokemon pokemon2) {
        if (pokemon1 == null || pokemon2 == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        Trainer trainer1 = pokemon1.getTrainer();
        Trainer trainer2 = pokemon2.getTrainer();

        trainer1.removePokemon(pokemon1);
        trainer1.addPokemon(pokemon2);

        trainer2.removePokemon(pokemon2);
        trainer1.addPokemon(pokemon1);

        pokemon1.setTrainer(trainer2);
        pokemon2.setTrainer(trainer1);

        pokemonDao.update(pokemon1);
        pokemonDao.update(pokemon2);

        trainerService.updateTrainer(trainer1);
        trainerService.updateTrainer(trainer2);
    }

    @Override
    public void deletePokemon(Pokemon pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon cannot be null.");
        }
        pokemonDao.delete(pokemon);
    }

    @Override
    public Pokemon getPokemonById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        return pokemonDao.findById(id);
    }

    @Override
    public List<Pokemon> getAllPokemons() {
        return Collections.unmodifiableList(pokemonDao.findAll());
    }

    @Override
    public List<Pokemon> getAllPokemonsOfTrainer(Trainer trainer) {
        return Collections.unmodifiableList(trainer.getPokemons());
    }

    @Override
    public List<Pokemon> getAllPokemonsWithName(String name) {
        List<Pokemon> all = this.getAllPokemons();
        for (Pokemon p : all) {
            if (!p.getName().equals(name)) {
                all.remove(p);
            }
        }
        return Collections.unmodifiableList(all);
    }

    @Override
    public List<Pokemon> getAllPokemonsWithType(PokemonType type) {
        List<Pokemon> all = this.getAllPokemons();
        for (Pokemon p : all) {
            if (!p.getType().equals(type)) {
                all.remove(p);
            }
        }
        return Collections.unmodifiableList(all);
    }
}
