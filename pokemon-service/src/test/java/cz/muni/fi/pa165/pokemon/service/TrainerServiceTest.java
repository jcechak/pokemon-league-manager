package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.PokemonDao;
import cz.muni.fi.pa165.pokemon.dao.TrainerDao;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Tournament;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Milos Bartak
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class TrainerServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Mock
    private TrainerDao trainerDao;
    
    @Mock
    private PokemonDao pokemonDao;
    
    @Mock
    private Trainer setUpTrainer;
    
    @Mock
    private Trainer setUpTrainer2;
    
    private Tournament tournament;
    
    @Mock
    private Stadium stadium;
    
    @Autowired
    @Mock
    private TrainerService trainerService;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void beforeMehod() {
        setUpTrainer = new Trainer();
        setUpTrainer.setName("Ash");
        setUpTrainer.setSurname("Brock");
        setUpTrainer.setId(12L);
        setUpTrainer.setDateOfBirth(new Date(1999-12-12));
        
        setUpTrainer2 = new Trainer();
        setUpTrainer2.setName("Misty");
        setUpTrainer2.setSurname("Mistic");
        setUpTrainer2.setId(15L);
        setUpTrainer2.setDateOfBirth(new Date(1998-10-10));
        
        List<Trainer> trainers = new ArrayList<Trainer>();
        trainers.add(setUpTrainer);
        trainers.add(setUpTrainer2);
        
        stadium = new Stadium();
        stadium.setCity("Pallet");
        stadium.setLeader(setUpTrainer);
        stadium.setType(PokemonType.ROCK);
        stadium.setId(156L);
        setUpTrainer.setStadium(stadium);
        
        Badge badge = new Badge();
        badge.setTrainer(setUpTrainer);
        badge.setStadium(stadium);
        List<Badge> badges = new ArrayList<Badge>();
        badges.add(badge);
        
        setUpTrainer.addBadge(badge);
        
        tournament = new Tournament();
        tournament.setMiminalPokemonCount(1);
        tournament.setTournamentName("MasterBlaster tournament");
        tournament.setTownName("Pallet");
        tournament.setMinimalPokemonLevel(17);
        tournament.setBadges(badges);
        
        
        when(trainerDao.findById(12L)).thenReturn(setUpTrainer);
        when(trainerDao.findAll()).thenReturn(trainers);
        when(trainerService.mayEnrollInTournament(setUpTrainer, tournament)).thenReturn(true);
        
        Mockito.doReturn(true).when(trainerService).isLeaderOfTheStadium(setUpTrainer, stadium);
        Mockito.doReturn(false).when(trainerService).isLeaderOfTheStadium(setUpTrainer2, stadium);
    }
    
    @Test
    public void testFindTrainerById() {
        Trainer trainer = trainerDao.findById(12L);
        
        assertEquals(trainer, setUpTrainer, "Failed to find the trainer by ID");
    }
    
    @Test
    public void testFindAllTrainers() {
        List<Trainer> trainers = new ArrayList<Trainer>();
        trainers.add(setUpTrainer);
        trainers.add(setUpTrainer2);
        
        List<Trainer> found = trainerDao.findAll();
        
        assertEquals(trainers, found, "Failed to find all trainers");
    }
    
    @Test
    public void testIsLeaderOfTheStadium() {
        Boolean isLeader = trainerService.isLeaderOfTheStadium(setUpTrainer, stadium);
        
        assertEquals(isLeader, Boolean.TRUE, "Trainer is not leader and should be");
        
        isLeader = trainerService.isLeaderOfTheStadium(setUpTrainer2, stadium);
        
        assertEquals(isLeader, Boolean.FALSE, "Trainer is leader and should not be");
    }
    
    /*@Test
    public void testAddPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Squirtle");
        pokemon.setNickname("Splash");
        pokemon.setSkillLevel(5);
        pokemon.setType(PokemonType.WATER);
        pokemonDao.create(pokemon);
        
        trainerService.addPokemon(setUpTrainer, pokemon);
    }*/
    
    @Test
    public void testMayEnrollInTournament() {
        
        
        Pokemon pokemon = new Pokemon();
        pokemon.setSkillLevel(25);
        setUpTrainer.addPokemon(pokemon);
        
        assertEquals(trainerService.mayEnrollInTournament(setUpTrainer, tournament), true);
        assertEquals(trainerService.mayEnrollInTournament(setUpTrainer2, tournament), false);
    }
    
}
