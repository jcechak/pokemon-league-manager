package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.exceptions.PokemonServiceException;
import cz.muni.fi.pa165.pokemon.dao.PokemonDao;
import cz.muni.fi.pa165.pokemon.dao.TrainerDao;
import cz.muni.fi.pa165.pokemon.entity.*;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Tests correctness of TrainerService methods
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
    
    @Mock
    private Pokemon pokemon;
    
    private Pokemon pokemon2;
    
    @Mock
    private Badge badge;
    
    @Mock
    private Tournament tournament;
    
    @Mock
    private Stadium stadium;
    
    private boolean called = false;
    
    @Autowired
    @InjectMocks
    private TrainerService trainerService;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @AfterClass
    public void tearDownClass() {
        
    }
    
    @BeforeMethod
    public void beforeMethod() {
        setUpTrainer = new Trainer();
        setUpTrainer.setName("Ash");
        setUpTrainer.setSurname("Brock");
        setUpTrainer.setId(12L);
        setUpTrainer.setDateOfBirth(new Date(1999-12-12));
        
        setUpTrainer2 = new Trainer();
        setUpTrainer2.setName("Ash");
        setUpTrainer2.setSurname("Mistic");
        setUpTrainer2.setId(15L);
        setUpTrainer2.setDateOfBirth(new Date(1998-10-10));
        
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(setUpTrainer);
        trainers.add(setUpTrainer2);
        
        stadium = new Stadium();
        stadium.setCity("Pallet");
        stadium.setLeader(setUpTrainer);
        stadium.setType(PokemonType.ROCK);
        stadium.setId(156L);
        setUpTrainer.setStadium(stadium);
        
        badge = new Badge();
        badge.setTrainer(setUpTrainer);
        badge.setStadium(stadium);
        List<Badge> badges = new ArrayList<>(); // TODO: badges are never used,
                                                // Please add unmodifiableList if you want to use it
        badges.add(badge);
        
        setUpTrainer.addBadge(badge);

        List<Trainer> trainersWithBadge = new ArrayList<>();
        trainersWithBadge.add(setUpTrainer);
        
        tournament = new Tournament();
        tournament.setMinimalPokemonCount(1);
        tournament.setTournamentName("MasterBlaster tournament");
        tournament.setStadiumId(157L);
        tournament.setMinimalPokemonLevel(17);
        tournament.addBadge(badge);
        
        pokemon = new Pokemon();
        pokemon.setName("Seal");
        pokemon.setNickname("Leas");
        pokemon.setType(PokemonType.WATER);
        pokemon.setSkillLevel(1);
        pokemon.setTrainer(setUpTrainer);
        
        
        List<Trainer> trainersWithSeal = new ArrayList<>();
        trainersWithSeal.add(setUpTrainer);
        
        List<Trainer> ashList = new ArrayList<>();
        ashList.add(setUpTrainer);
        ashList.add(setUpTrainer2);
        
        List<Trainer> misticList = new ArrayList<>();
        misticList.add(setUpTrainer);
        
        when(trainerDao.findById(12L)).thenReturn(setUpTrainer);
        when(trainerDao.findAll()).thenReturn(Collections.unmodifiableList(trainers));
        when(trainerDao.findAllTrainersWithPokemon(pokemon)).thenReturn(Collections.unmodifiableList(trainersWithSeal));
        when(trainerDao.findAllTrainersWithBadge(badge)).thenReturn(Collections.unmodifiableList(trainersWithBadge));
        when(trainerDao.findAllTrainersWithName("Ash")).thenReturn(Collections.unmodifiableList(ashList));
        when(trainerDao.findAllTrainersWithSurname("Mistic")).thenReturn(Collections.unmodifiableList(misticList));
        doAnswer(invocation -> {
            called = true;
            return null;
        }).when(trainerDao).create(setUpTrainer);
        doAnswer(invocation -> {
            called = true;
            return null;
        }).when(trainerDao).delete(setUpTrainer);
        
    }
    
    @AfterMethod
    public void tearDownMethod() {
        pokemonDao.delete(pokemon2);
        called = false;
    }
    
    @Test
    public void testCreateTrainer() {
        trainerService.createTrainer(setUpTrainer);
        
        assertEquals(called, true, "No dao create method called");
    }
    
    @Test
    public void testDeleteTrainer() {
        trainerService.deleteTrainer(setUpTrainer);
        
        assertEquals(called, true, "No dao delete method called");
    }
    
    @Test
    public void testFindTrainerById() {
        Trainer trainer = trainerService.findTrainerById(12L);
        
        assertEquals(trainer, setUpTrainer, "Failed to find the trainer " + setUpTrainer.toString() + " by ID");
    }
    
    @Test
    public void testFindAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(setUpTrainer);
        trainers.add(setUpTrainer2);
        
        List<Trainer> found = trainerService.findAllTrainers();
        
        assertEquals(trainers, found, "Failed to find all trainers");
    }
    
    @Test
    public void testIsLeaderOfTheStadium() {
        Boolean isLeader = trainerService.isLeaderOfTheStadium(setUpTrainer, stadium);
        
        assertEquals(isLeader, Boolean.TRUE, "Trainer " + setUpTrainer.toString() + " is not leader and should be");
        
        isLeader = trainerService.isLeaderOfTheStadium(setUpTrainer2, stadium);
        
        assertEquals(isLeader, Boolean.FALSE, "Trainer " + setUpTrainer2.toString() + " is leader and should not be");
    }
    
    @Test
    public void testAddPokemon() {
        pokemon2 = new Pokemon();
        pokemon2.setName("Squirtle");
        pokemon2.setNickname("Splash");
        pokemon2.setSkillLevel(5);
        pokemon2.setType(PokemonType.WATER);
        pokemonDao.create(pokemon2);
        
        
        trainerService.addPokemon(setUpTrainer, pokemon2);
        
        assertEquals(setUpTrainer.getPokemons().contains(pokemon2), true, 
                "Pokemon " + pokemon2.toString() + " was not added to trainer " + setUpTrainer.toString());
    }
    
    @Test
    public void testAddBadge() {
        assertEquals(setUpTrainer.getBadges().contains(badge), true, 
                "Badge " + badge.toString() + " was not added to trainer " + setUpTrainer.toString());
    }
    
    @Test(expectedExceptions = PokemonServiceException.class)
    public void testtAddPokemonTwice() {
        pokemon2 = new Pokemon();
        pokemon2.setName("Squirtle");
        pokemon2.setNickname("Splash");
        pokemon2.setSkillLevel(5);
        pokemon2.setType(PokemonType.WATER);
        pokemonDao.create(pokemon2);
        
        
        trainerService.addPokemon(setUpTrainer, pokemon2);
        trainerService.addPokemon(setUpTrainer, pokemon2);
    }
    
    @Test
    public void testRemovePokemon() {
        Pokemon pokemon5 = new Pokemon();
        pokemon5.setName("Onix");
        pokemon5.setNickname("The Rock");
        pokemon5.setSkillLevel(5);
        pokemon5.setType(PokemonType.ROCK);
        pokemonDao.create(pokemon5);
        
        Pokemon pokemon6 = new Pokemon();
        pokemon6.setName("Dito");
        pokemon6.setNickname("DD");
        pokemon6.setSkillLevel(5);
        pokemon6.setType(PokemonType.NORMAL);
        pokemonDao.create(pokemon6);
        
        setUpTrainer.addPokemon(pokemon5);
        setUpTrainer.addPokemon(pokemon6);
        
        assertEquals(setUpTrainer.getPokemons().size(), 2);
        setUpTrainer.removePokemon(pokemon5);
        assertEquals(setUpTrainer.getPokemons().size(), 1);
        setUpTrainer.removePokemon(pokemon5);
        assertEquals(setUpTrainer.getPokemons().size(), 1);
    }
    
    @Test
    public void testMayEnrollInTournament() {
        Pokemon pokemon = new Pokemon();
        pokemon.setSkillLevel(25);
        setUpTrainer.addPokemon(pokemon);
        
        TrainerService trainerService2 = new TrainerServiceImpl();
        
        assertEquals(trainerService2.mayEnrollInTournament(setUpTrainer, tournament), true, 
                "Trainer " + setUpTrainer.toString() + " may enroll in tournament, but was not able to");
        assertEquals(trainerService2.mayEnrollInTournament(setUpTrainer2, tournament), false, 
                "Trainer " + setUpTrainer2.toString() + " must not enroll in tournament, but was able to");
    }
 
    @Test
    public void testFindAllTrainersWithPokemon() {
        List<Trainer> trainersWithSeal = new ArrayList<>();
        trainersWithSeal.add(setUpTrainer);
        assertEquals(trainerService.findAllTrainersWithPokemon(pokemon), trainersWithSeal, 
                "Did not found all trainers with pokemon" + pokemon.toString());
    }
    
    @Test
    public void testFindAllTrainersWithBadge() {
        List<Trainer> trainersWithBadge = new ArrayList<>();
        trainersWithBadge.add(setUpTrainer);
        assertEquals(trainerService.findAllTrainersWithBadge(badge), trainersWithBadge,
                "Did not found all trainers with badge" + badge.toString());
    }

    @Test
    public void testFindAllTrainersWithName() {
        List<Trainer> ashList = new ArrayList<>();
        ashList.add(setUpTrainer);
        ashList.add(setUpTrainer2);
        assertEquals(trainerService.findAllTrainersWithName("Ash"), ashList,
                "Did not found all trainers with name \"Ash\"");
    }

    @Test
    public void testFindAllTrainersWithSurname() {
        List<Trainer> misticList = new ArrayList<>();
        misticList.add(setUpTrainer);
        assertEquals(trainerService.findAllTrainersWithSurname("Mistic"), misticList,
                "Did not found all trainers with surname \"Mistic\"");
    }
}
