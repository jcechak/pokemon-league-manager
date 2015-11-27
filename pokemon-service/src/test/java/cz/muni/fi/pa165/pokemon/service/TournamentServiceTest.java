package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.TournamentDao;
import cz.muni.fi.pa165.pokemon.entity.Tournament;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * tests corectness of Tournament service
 * 
 * @author Milos Bartak
 */
public class TournamentServiceTest {
    
    @Mock
    private TournamentDao tournamentDao;
    
    @Autowired
    @InjectMocks
    private TournamentService tournamentService = new TournamentServiceImpl();
    
    private Tournament tournament;
    private boolean called = false;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void beforeMethod() {
        tournament = new Tournament();
        tournament.setMinimalPokemonCount(5);
        tournament.setMinimalPokemonLevel(1);
        tournament.setStadiumId(Long.MIN_VALUE);
        tournament.setTournamentName("turnaj");
        
        doAnswer(invocation -> {
            called = true;
            return null;
        }).when(tournamentDao).create(tournament);
        
        doAnswer(invocation -> {
            called = true;
            return null;
        }).when(tournamentDao).delete(tournament);
        
        doAnswer(invocation -> {
            tournament.setMinimalPokemonCount(3);
            return null;
        }).when(tournamentDao).update(tournament);
        
        when(tournamentDao.findById(Long.MIN_VALUE)).thenReturn(tournament);
    }
    
    @AfterMethod
    public void tearDown() {
        called = false;
    }
    
    @Test
    public void testCreateTournament() {
        tournamentService.create(tournament);
        
        assertEquals(called, true, "No dao create method called");
    }
    
    @Test
    public void testDeleteTournament() {
        tournamentService.delete(tournament);
        
        assertEquals(called, true, "No dao delete method called");
    }
    
    @Test
    public void testFindTournamentById() {
        Tournament found = tournamentService.findById(Long.MIN_VALUE);
        
        assertEquals(found, tournament, "Tournament not found");
    }
    
    @Test
    public void testUpdateTournament() {
        Tournament found = tournamentService.findById(Long.MIN_VALUE);
        found.setMinimalPokemonCount(3);
        tournamentService.update(tournament);
        found = tournamentService.findById(Long.MIN_VALUE);
        
        assertEquals(found.getMinimalPokemonCount(), 3, "tournament not updated");
    }
}
