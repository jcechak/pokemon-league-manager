package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.exceptions.PokemonServiceException;
import cz.muni.fi.pa165.pokemon.dao.TrainerDao;
import cz.muni.fi.pa165.pokemon.entity.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of the TrainerService providing service to facade layer.
 * 
 * @author Milos Bartak
 */
@Service
public class TrainerServiceImpl implements TrainerService {
    @Inject
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
        if(trainer == null || stadium == null) {
            return false;
        }
        return stadium.equals(trainer.getStadium());
    }

    @Override
    public boolean mayEnrollInTournament(Trainer trainer, Tournament tournament) {
        if(trainer.getPokemons().size() < tournament.getMinimalPokemonCount()) {
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
        if(trainer.getPokemons().contains(pokemon)) {
            throw new PokemonServiceException(trainer.toString() + 
            " already has " + pokemon.toString());
        }
        trainer.addPokemon(pokemon);
    }
    
    @Override
    public void removePokemon(Trainer trainer, Pokemon pokemon) {
        trainer.removePokemon(pokemon);
    }

    @Override
    public void addBadge(Trainer trainer, Badge badge) {
        if(trainer.getBadges().contains(badge)) {
            throw new PokemonServiceException(trainer.toString() + 
                    " already has " + badge.toString());
        }
        
        if(!(this.isLeaderOfTheStadium(trainer, badge.getStadium()))) {
            trainer.addBadge(badge);
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
        return trainerDao.findAllTrainersWithPokemon(pokemon);
    }

    @Override
    public Collection<Trainer> findAllTrainersWithBadge(Badge badge) {
        return trainerDao.findAllTrainersWithBadge(badge);
    }

    @Override
    public Collection<Trainer> findAllTrainersWithName(String name) {
        return trainerDao.findAllTrainersWithName(name);
    }

    @Override
    public Collection<Trainer> findAllTrainersWithSurname(String surname) {
        return trainerDao.findAllTrainersWithSurname(surname);
    }
    
}
