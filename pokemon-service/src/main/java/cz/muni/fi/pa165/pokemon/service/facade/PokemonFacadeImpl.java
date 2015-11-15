package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
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

    @Override
    public PokemonDTO createPokemon(PokemonCreateDTO pokemon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PokemonDTO getPokemonById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeSkill(PokemonDTO pokemon, int newSkill) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changeTrainer(PokemonDTO pokemon, TrainerDTO newTrainer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePokemon(PokemonDTO pokemon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PokemonDTO> getAllPokemons() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PokemonDTO> getAllPokemonsOfTrainer(TrainerDTO trainer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PokemonDTO> getAllPokemonsWithName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PokemonDTO> getAllPokemonsWtihType(PokemonType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
