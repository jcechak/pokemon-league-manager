package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.TournamentDao;
import cz.muni.fi.pa165.pokemon.entity.Tournament;
import javax.inject.Inject;

/**
 * Service class for tournament
 * 
 * @author Milos Bartak
 */
public class TournamentServiceImpl implements TournamentService {

    @Inject
    private TournamentDao tournamentDao;
    @Override
    public void create(Tournament tournament) {
        tournamentDao.create(tournament);
    }

    @Override
    public void update(Tournament tournament) {
        tournamentDao.update(tournament);
    }

    @Override
    public void delete(Tournament tournament) {
        tournamentDao.delete(tournament);
    }

    @Override
    public Tournament findById(Long id) {
        return tournamentDao.findById(id);
    }
    
}
