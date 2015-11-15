package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.PokemonDao;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Implementation of Pokemon service interface.
 *
 * @author Jaroslav Cechak
 */
@Service
public class PokemonServiceImpl implements PokemonService {
    
    @Inject
    private PokemonDao pokemonDao;

    @Override
    public void createPokemon(Pokemon pokemon) {
        pokemonDao.create(pokemon);
    }

    @Override
    public void updatePokemon(Pokemon pokemon) {
        pokemonDao.update(pokemon);
    }

    @Override
    public void deletePokemon(Pokemon pokemon) {
        pokemonDao.delete(pokemon);
    }

    @Override
    public Pokemon getPokemonById(Long id) {
        return pokemonDao.findById(id);
    }

    @Override
    public List<Pokemon> getAllPokemons() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
