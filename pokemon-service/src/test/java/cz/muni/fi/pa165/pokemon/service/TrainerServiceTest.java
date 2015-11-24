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
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
 * Tests corectness of TrainerService methods
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
    
    @Mock
    private Badge badge;
    
    @Mock
    private Tournament tournament;
    
    @Mock
    private Stadium stadium;
    
    @Autowired
    @InjectMocks
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
        setUpTrainer2.setName("Ash");
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
        
        badge = new Badge();
        badge.setTrainer(setUpTrainer);
        badge.setStadium(stadium);
        List<Badge> badges = new ArrayList<Badge>();
        badges.add(badge);
        
        setUpTrainer.addBadge(badge);

        List<Trainer> trainersWithBadge = new ArrayList<Trainer>();
        trainersWithBadge.add(setUpTrainer);
        
        tournament = new Tournament();
        tournament.setMiminalPokemonCount(1);
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
        
        
        List<Trainer> trainersWithSeal = new ArrayList<Trainer>();
        trainersWithSeal.add(setUpTrainer);
        
        List<Trainer> ashList = new ArrayList<Trainer>();
        ashList.add(setUpTrainer);
        ashList.add(setUpTrainer2);
        
        List<Trainer> misticList = new ArrayList<Trainer>();
        misticList.add(setUpTrainer);
        
        when(trainerDao.findById(12L)).thenReturn(setUpTrainer);
        when(trainerDao.findAll()).thenReturn(trainers);
        when(trainerDao.findAllTrainersWithPokemon(pokemon)).thenReturn(trainersWithSeal);
        when(trainerDao.findAllTrainersWithBadge(badge)).thenReturn(trainersWithBadge);
        when(trainerDao.findAllTrainersWithName("Ash")).thenReturn(ashList);
        when(trainerDao.findAllTrainersWithSurname("Mistic")).thenReturn(misticList);
        
        
    }
    
    @Test
    public void testFindTrainerById() {
        Trainer trainer = trainerDao.findById(12L);
        
        assertEquals(trainer, setUpTrainer, "Failed to find the trainer " + setUpTrainer.toString() + " by ID");
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
        
        assertEquals(isLeader, Boolean.TRUE, "Trainer " + setUpTrainer.toString() + " is not leader and should be");
        
        isLeader = trainerService.isLeaderOfTheStadium(setUpTrainer2, stadium);
        
        assertEquals(isLeader, Boolean.FALSE, "Trainer " + setUpTrainer2.toString() + " is leader and should not be");
    }
    
    @Test
    public void testAddPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Squirtle");
        pokemon.setNickname("Splash");
        pokemon.setSkillLevel(5);
        pokemon.setType(PokemonType.WATER);
        pokemonDao.create(pokemon);
        
        
        trainerService.addPokemon(setUpTrainer, pokemon);
        
        assertEquals(setUpTrainer.getPokemons().contains(pokemon), true, 
                "Pokemon " + pokemon.toString() + " was not added to trainer " + setUpTrainer.toString());
    }
    
    @Test
    public void testAddBadge() {
        assertEquals(setUpTrainer.getBadges().contains(badge), true, 
                "Badge " + badge.toString() + " was not added to trainer " + setUpTrainer.toString());
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
                "Trainer " + setUpTrainer2.toString() + " must not enroll in tournamet, but was able to");
    }
 
    @Test
    public void testFindfindAllTrainersWithPokemon() {
        List<Trainer> trainersWithSeal = new ArrayList<Trainer>();
        trainersWithSeal.add(setUpTrainer);
        assertEquals(trainerDao.findAllTrainersWithPokemon(pokemon), trainersWithSeal, 
                "Did not found all trainers with pokemon" + pokemon.toString());
    }
    
    @Test
    public void testFindAllTrainersWithBadge() {
        List<Trainer> trainersWithBadge = new ArrayList<Trainer>();
        trainersWithBadge.add(setUpTrainer);
        assertEquals(trainerDao.findAllTrainersWithBadge(badge), trainersWithBadge,
                "Did not found all trainers with badge" + badge.toString());
    }
    
    @Test
    public void testFindAllTrainersWithName() {
        List<Trainer> ashList = new ArrayList<Trainer>();
        ashList.add(setUpTrainer);
        ashList.add(setUpTrainer2);
        assertEquals(trainerDao.findAllTrainersWithName("Ash"), ashList,
                "Did not found all trainers with name \"Ash\"");
    }
    
    @Test
    public void testFindAllTrainersWithSurname() {
        List<Trainer> misticList = new ArrayList<Trainer>();
        misticList.add(setUpTrainer);
        assertEquals(trainerDao.findAllTrainersWithSurname("Mistic"), misticList,
                "Did not found all trainers with surname \"Mistic\"");
    }
}
