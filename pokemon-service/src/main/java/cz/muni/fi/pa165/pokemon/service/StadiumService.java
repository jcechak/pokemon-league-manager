package cz.muni.fi.pa165.pokemon.service;
import cz.muni.fi.pa165.pokemon.entity.Stadium;

import java.util.List;

/**
 * @author Dominika Talianova
 */
public interface StadiumService {

    void createStadium(Stadium stadium);

    void updateStadium(Stadium stadium);

    void deleteStadium(Stadium stadium);

    Stadium getStadiumById(Long id);

    List<Stadium> getAll();
}
