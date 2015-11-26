package cz.muni.fi.pa165.pokemon.service;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Tournament;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;

import java.util.List;

/**
 * Interface for StadiumServiceImpl class providing service to facade layer.
 * @author Dominika Talianova
 */
public interface StadiumService {

    /**
     * creates stadium in the DB
     * @param stadium stadium to be created
     */
    void createStadium(Stadium stadium);

    /**
     * updates stadium in the DB
     * @param stadium stadium to be updated
     */
    void updateStadium(Stadium stadium);

    /**
     * deletes stadium from the DB
     * @param stadium stadium to be deleted
     */
    void deleteStadium(Stadium stadium);

    /**
     * finds stadium by given id in the DB
     * @param id the id used in search
     * @return found stadium
     */
    Stadium getStadiumById(Long id);

    /**
     * gets all stadium in the DB
     * @return list of stadiums
     */
    List<Stadium> getAll();

    List <Stadium> findByType(PokemonType type);

    Stadium findByCity(String city);

    Stadium findByLeader(Trainer leader);

    String getLeaderInfo(Trainer trainer);

    Trainer getTheLeader(Stadium stadium);

    boolean hasLeader(Stadium stadium);

}
