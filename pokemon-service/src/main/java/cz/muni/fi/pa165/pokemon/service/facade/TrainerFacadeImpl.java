package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Milos Bartak
 */
@Service
@Transactional
public class TrainerFacadeImpl implements TrainerFacade{
    
    @Autowired
    private TrainerService trainerService;
    
    @Override
    public void createTrainer(TrainerDTO trainer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteTrainer(TrainerDTO trainer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateTrainer(TrainerDTO trainer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isLeaderOfTheStadium(StadiumDTO stadium) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPokemon(PokemonDTO pokemon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addBadge(BadgeDTO badge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TrainerDTO findTrainerById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TrainerDTO> findAllTrainers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TrainerDTO> findAllTrainersWithPokemon(PokemonDTO pokemon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TrainerDTO> findAllTrainersWithBadge(BadgeDTO badge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TrainerDTO> findAllTrainersWithName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<TrainerDTO> findAllTrainersWithSurname(String surname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
