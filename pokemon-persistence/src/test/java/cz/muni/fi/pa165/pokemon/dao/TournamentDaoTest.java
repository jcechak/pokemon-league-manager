package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration;
import cz.muni.fi.pa165.pokemon.entity.Tournament;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

/**
 * test if implementation of TournamentDao works
 * 
 * @author Milos Bartak
 */
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class TournamentDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceUnit
    private EntityManagerFactory emf;

    private EntityManager entityManager;

    @Inject
    private TournamentDao tournamentDao;
    
    @Test
    public void testCreate() {
        entityManager = emf.createEntityManager();
        
        Tournament tournament = new Tournament();
        tournament.setMinimalPokemonCount(1);
        tournament.setMinimalPokemonLevel(5);
        tournament.setStadiumId(Long.MIN_VALUE);
        tournament.setTournamentName("namakanejTurnaj");
        
        tournamentDao.create(tournament);
        
        Tournament found = entityManager.find(Tournament.class, tournament.getId());
        
        assertEquals(found
                        , tournament, "Persisted tournament with id: " +
                        tournament.getId() +
                        "does not match found tournament with id: " +
                        found.getId());
        
        entityManager.detach(tournament);
    }
    
    @Test
    public void testUpdate() {
        entityManager = emf.createEntityManager();
        
        Tournament tournament = new Tournament();
        tournament.setMinimalPokemonCount(1);
        tournament.setMinimalPokemonLevel(5);
        tournament.setStadiumId(Long.MIN_VALUE);
        tournament.setTournamentName("namakanejTurnaj");
        
        tournamentDao.create(tournament);
        tournament.setMinimalPokemonCount(15);
        tournamentDao.update(tournament);
        
        Tournament found = entityManager.find(Tournament.class, tournament.getId());
        
        assertEquals(found.getMinimalPokemonCount(), tournament.getMinimalPokemonCount(),
                "Tournament is not updated properly");
        
        entityManager.detach(tournament);
    }
    
    @Test
    public void testDelete() {
        entityManager = emf.createEntityManager();
        
        Tournament tournament = new Tournament();
        tournament.setMinimalPokemonCount(1);
        tournament.setMinimalPokemonLevel(5);
        tournament.setStadiumId(Long.MIN_VALUE);
        tournament.setTournamentName("namakanejTurnaj");
        
        tournamentDao.create(tournament);
        tournamentDao.delete(tournament);
        
        Tournament found = entityManager.find(Tournament.class, tournament.getId());
        assertEquals(found, null, "tournament was not deleted");
    }
    
    @Test
    public void testFindById() {
        entityManager = emf.createEntityManager();
        
        Tournament tournament = new Tournament();
        tournament.setMinimalPokemonCount(1);
        tournament.setMinimalPokemonLevel(5);
        tournament.setStadiumId(Long.MIN_VALUE);
        tournament.setTournamentName("namakanejTurnaj");
        
        tournamentDao.create(tournament);
        Tournament found = entityManager.find(Tournament.class, tournament.getId());
        Tournament found2 = tournamentDao.findById(tournament.getId());
        
        assertEquals(found, found2, "tournament not found");
    }
}
