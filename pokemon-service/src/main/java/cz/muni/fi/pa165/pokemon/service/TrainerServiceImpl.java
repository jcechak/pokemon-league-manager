package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.TrainerDao;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Tournament;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Milos Bartak
 */
@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerDao trainerDao;
    
    @Override
    public void createTrainer(Trainer trainer) {
        trainerDao.create(trainer);
    }

    @Override
    public void deleteTrainer(Trainer trainer) {
        trainerDao.delete(trainer);
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        trainerDao.update(trainer);
    }

    @Override
    public boolean isLeaderOfTheStadium(Trainer trainer, Stadium stadium) {
        return trainer.getStadium().equals(stadium);
    }

    @Override
    public boolean mayEnrollInTournament(Trainer trainer, Tournament tournament) {
        if(trainer.getPokemons().size() < tournament.getMiminalPokemonCount()) {
            return false;
        }
        
        for(Pokemon p : trainer.getPokemons()) {
            if(p.getSkillLevel() < tournament.getMinimalPokemonLevel()) {
                return false;
            }
        }
        
        for(Badge b : tournament.getBadges()) {
            if(!(trainer.getBadges().contains(b))) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public void addPokemon(Trainer trainer, Pokemon pokemon) {
        trainer.addPokemon(pokemon);
        //pokemon.setTrainer(trainer);
        trainerDao.update(trainer);
    }

    //TODO dao call - method for adding badge
    @Override
    public void addBadge(Trainer trainer, Badge badge) {
        if(!(this.isLeaderOfTheStadium(trainer, badge.getStadium()))) {
            trainer.addBadge(badge);
            trainerDao.update(trainer);
        }
    }

    @Override
    public Trainer findTrainerById(Long id) {
        return trainerDao.findById(id);
    }

    @Override
    public List<Trainer> findAllTrainers() {
        return trainerDao.findAll();
    }

    @Override
    public Collection<Trainer> findAllTrainersWithPokemon(Pokemon pokemon) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Trainer> findAllTrainersWithBadge(Badge badge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Trainer> findAllTrainersWithName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Trainer> findAllTrainersWithSurname(String surname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
