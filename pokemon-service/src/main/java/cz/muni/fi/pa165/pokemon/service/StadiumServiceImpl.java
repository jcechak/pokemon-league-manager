package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.StadiumDao;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of StadiumService providing service to facade layer.
 * @author Dominika Talianova
 */
@Service
public class StadiumServiceImpl implements StadiumService {

    @Inject
    private StadiumDao stadiumDao;

    @Override
    public void createStadium(Stadium stadium){
        stadiumDao.create(stadium);
    }

    @Override
    public void updateStadium(Stadium stadium){
        stadiumDao.update(stadium);
    }

    @Override
    public void deleteStadium(Stadium stadium){
        stadiumDao.delete(stadium);
    }

    @Override
    public Stadium getStadiumById(Long id){
        return (stadiumDao.findById(id));
    }

    @Override
    public List<Stadium> getAll(){
        return (stadiumDao.findAll());
    }

    @Override
    public List<Stadium> findByType(PokemonType type){
        return (stadiumDao.findByPokemonType(type));
    }

    @Override
    public Stadium findByCity(String city){
        return (stadiumDao.findByCity(city));
    }

    @Override
    public Stadium findByLeader(Trainer leader){
        return (stadiumDao.findByStadiumLeader(leader));
    }

    @Override
    public String getLeaderInfo(Trainer trainer){
        return trainer.toString();
    }

    @Override
    public Trainer getTheLeader(Stadium stadium){
        return stadium.getLeader();
    }

    @Override
    public boolean hasLeader(Stadium stadium){
        if(stadium.getLeader()== null) { return false; }
        return true;
    }



}
