package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeClass;

/**
 * Tests correctness of TrainerFacadeImpl methods
 * 
 * @author Milos Bartak
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class TrainerFacadeImplNGTest extends AbstractTestNGSpringContextTests{
    
    @InjectMocks
    private TrainerFacade trainerFacade = new TrainerFacadeImpl();
    
    @Mock
    private TrainerService trainerService;
    
    @Mock
    private MappingService beanMappingService;
    
    private Trainer setUpTrainer;
    private TrainerDTO trainerDTO;
    private StadiumDTO stadiumDTO;
    private List<Trainer> trainersList;
    private List<TrainerDTO> trainersDTOList;
    private Pokemon pokemon;
    private PokemonDTO pokemonDTO;
    private BadgeDTO badgeDTO;
    private Badge badge;
    
    private Stadium stadium = null;
    
    private boolean called = false;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void setUpMethod() {
        setUpTrainer = new Trainer();
        setUpTrainer.setId(12L);
        setUpTrainer.setName("Brock");
        setUpTrainer.setSurname("Brokovnice");
        setUpTrainer.setDateOfBirth(Date.valueOf("1989-10-11"));
        
        trainerDTO = TestUtils.createTrainerDTO(setUpTrainer);
        trainersList = new ArrayList<Trainer>();
        trainersDTOList = new ArrayList<TrainerDTO>();
        trainersList.add(setUpTrainer);
        trainersDTOList.add(trainerDTO);
        
        pokemon = new Pokemon();
        pokemon.setName("Pika");
        pokemon.setNickname("Chu");
        pokemon.setSkillLevel(5);
        pokemon.setType(PokemonType.ELECTRIC);
        
        
        when(beanMappingService.map(trainerDTO, Trainer.class)).thenReturn(setUpTrainer);
        when(beanMappingService.map(setUpTrainer, TrainerDTO.class)).thenReturn(trainerDTO);
        when(beanMappingService.map(stadiumDTO, Stadium.class)).thenReturn(stadium);
        when(beanMappingService.map(trainersList, TrainerDTO.class)).thenReturn(trainersDTOList);
        when(beanMappingService.map(pokemonDTO, Pokemon.class)).thenReturn(pokemon);
        when(beanMappingService.map(trainerService.findAllTrainersWithPokemon(beanMappingService.
                map(pokemonDTO, Pokemon.class)), TrainerDTO.class)).thenReturn(trainersDTOList);
        when(beanMappingService.map(badgeDTO, Badge.class)).thenReturn(badge);
        when(beanMappingService.map(trainerService.findAllTrainersWithBadge(beanMappingService.
                map(badgeDTO, Badge.class)), TrainerDTO.class)).thenReturn(trainersDTOList);
        
        doAnswer(invocation -> {
            called = true;
            return null;
        }).when(trainerService).createTrainer(setUpTrainer);
        
        doAnswer(invocation -> {
            called = true;
            return null;
        }).when(trainerService).deleteTrainer(setUpTrainer);
        
        doAnswer(invocation -> {
            trainerDTO.setName(setUpTrainer.getName());
            return null;
        }).when(trainerService).updateTrainer(setUpTrainer);
        
        when(trainerService.isLeaderOfTheStadium(setUpTrainer, stadium)).thenReturn(true);
        when(trainerService.findTrainerById(setUpTrainer.getId())).thenReturn(setUpTrainer);
        when(trainerService.findAllTrainers()).thenReturn(trainersList);
        when(trainerService.findAllTrainersWithName("Brock")).thenReturn(trainersList);
        when(trainerService.findAllTrainersWithSurname("Brokovnice")).thenReturn(trainersList);
        when(trainerService.findAllTrainersWithPokemon(pokemon)).thenReturn(trainersList);
        when(trainerService.findAllTrainersWithBadge(badge)).thenReturn(trainersList);
        
        doAnswer(invocation -> {
            trainerDTO.addPokemon(pokemonDTO);
            return null;
        }).when(trainerService).addPokemon(setUpTrainer, pokemon);
        
        doAnswer(invocation -> {
            trainerDTO.removePokemon(pokemonDTO);
            return null;
        }).when(trainerService).removePokemon(setUpTrainer, pokemon);
        
        doAnswer(invocation -> {
            trainerDTO.addBadge(badgeDTO);
            return null;
        }).when(trainerService).addBadge(setUpTrainer, badge);
    }
    
    @AfterMethod
    public void tearDownMethod() {
        called = false;
    }
    
    /**
     * Tests creation of trainer
     */
    @Test
    public void testCreateTrainer() {
        trainerFacade.createTrainer(trainerDTO);

        assertEquals(called, true,  trainerDTO.toString() + " not created");
    }
    
    /**
     * Tests deletion of trainer
     */
    @Test
    public void testDeleteTrainer() {
        trainerFacade.deleteTrainer(trainerDTO);
        
        assertEquals(called, true,  trainerDTO.toString() + " not deleted");
    }
    
    /**
     * Test update of trainer
     */
    @Test
    public void testUpdateTrainer() {
        setUpTrainer.setName("Brockbrock");
        trainerFacade.updateTrainer(trainerDTO);
        
        assertEquals(setUpTrainer.getName(), trainerDTO.getName(), trainerDTO.toString() + " not updated");
    }
    
    /**
     * Test wheather trainer is leader of stadium
     */
    @Test
    public void testIsLeaderOfTheStadium() {
        stadium = new Stadium();
        stadium.setCity("Pallet");
        stadium.setType(PokemonType.ROCK);
        stadium.setLeader(setUpTrainer);
        
        stadiumDTO = TestUtils.createStadiumDTO(stadium);
        
        setUpTrainer.setStadium(stadium);
        boolean result = trainerFacade.isLeaderOfTheStadium(trainerDTO, stadiumDTO);
        
        assertEquals(result, true, trainerDTO.toString() + " is not the leader and should be");
    }
    
    /**
     * Test adding pokemon to trainer
     */
    @Test
    public void testAddPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Pika");
        pokemon.setNickname("Chu");
        pokemon.setSkillLevel(77);
        pokemon.setType(PokemonType.ELECTRIC);
        pokemon.setTrainer(setUpTrainer);

        trainerFacade.addPokemon(trainerDTO, pokemonDTO);
        
        List<PokemonDTO> list = new ArrayList<>();
        list.add(pokemonDTO);
        
        assertEquals(trainerDTO.getPokemons().size(), 1, "Pokemon not added.");
        assertEquals(trainerDTO.getPokemons(), list, "Pokemon not added, pokemon lists dont match");
        
    }
    
    /**
     * Tests removing pokemon from trainer
     */
    @Test
    public void testRemovePokemon() {
        trainerFacade.removePokemon(trainerDTO, pokemonDTO);
        assertEquals(trainerDTO.getPokemons().size(), 0, "Pokemon not removed.");
    }
   
    /**
     * Tests adding badge to trainer
     */
    @Test
    public void testAddBadge() {
        badge = new Badge();
        badgeDTO = new BadgeDTO();
        badgeDTO.setStadiumId(1L);
        badgeDTO.setTrainerId(111L);
        
        trainerFacade.addBadge(trainerDTO, badgeDTO);
        
        List<BadgeDTO> list = new ArrayList<>();
        list.add(badgeDTO);
        
        assertEquals(trainerDTO.getBadges().size(), 1, "Badge not added");
        assertEquals(trainerDTO.getBadges(), list);
    }
    
    /**
     * Tests finding trainer by id
     */
    @Test
    public void testFindTrainerById() {
        Long id = setUpTrainer.getId();
        
        TrainerDTO found = trainerFacade.findTrainerById(id);
        
        assertEquals(found, trainerDTO, setUpTrainer.toString() + " not found");
    }
    
    /**
     * Tests finding all trainers
     */
    @Test
    public void testFindAllTrainers() {
        Collection<TrainerDTO> resultList = trainerFacade.findAllTrainers();
        assertEquals(resultList.size(), 1, "result list is not of same length");
        assertEquals(resultList, trainersDTOList, "result list does not contain same objects");
    }
    
    /**
     * Test findig all trainers with given name
     */
    @Test
    public void testFindAllTrainersWithName() {
        TrainerDTO trainerDTO2 = new TrainerDTO();
        trainerDTO2.setName("Brock");
        trainerDTO2.setSurname("Master");
        trainerDTO2.setDateOfBirth(Date.valueOf(LocalDate.MIN));
        trainersDTOList.add(trainerDTO2);
        
        Trainer trainer = new Trainer();
        trainer.setName(trainerDTO2.getName());
        trainer.setDateOfBirth(trainerDTO2.getDateOfBirth());
        trainer.setSurname(trainerDTO2.getSurname());
        trainersList.add(trainer);
        
        
        Collection<TrainerDTO> result = trainerFacade.findAllTrainersWithName("Brock");
        assertEquals(result.size(), 2, "result list is not of same length");
        assertEquals(result, trainersDTOList, "result list does not contain same objects");
        trainersDTOList.remove(trainerDTO2);
        trainersList.remove(trainer);
    }
    
    /**
     * Tests find all trainers with surname
     */
    @Test
    public void testFindAllTrainersWithSurName() {
        TrainerDTO trainerDTO2 = new TrainerDTO();
        trainerDTO2.setName("Borec");
        trainerDTO2.setSurname("Brokovnice");
        trainerDTO2.setDateOfBirth(Date.valueOf(LocalDate.MIN));
        trainersDTOList.add(trainerDTO2);
        
        Trainer trainer = new Trainer();
        trainer.setName(trainerDTO2.getName());
        trainer.setDateOfBirth(trainerDTO2.getDateOfBirth());
        trainer.setSurname(trainerDTO2.getSurname());
        trainersList.add(trainer);
        
        Collection<TrainerDTO> result = trainerFacade.findAllTrainersWithSurname("Brokovnice");
        assertEquals(result, trainersDTOList, "result list does not contain same objects");
    }
    
    
    /**
     * Tests find all trainers with pokemon
     */
    @Test
    public void testFindAllTrainersWithPokemon() {
        pokemonDTO = TestUtils.createPokemonDTO(pokemon);
        
        
        trainerDTO.addPokemon(pokemonDTO);
        
        Collection<TrainerDTO> result = trainerFacade.findAllTrainersWithPokemon(pokemonDTO);
        assertEquals(result, trainersDTOList);
    }
    
    /**
     * Tests find all trainers with badge
     */
    @Test
    public void testFindAllTrainersWithBadge() {
        badgeDTO = new BadgeDTO();
        badgeDTO.setStadiumId(1L);
        badgeDTO.setTrainerId(111L);
        
        trainerDTO.addBadge(badgeDTO);
        
        Collection<TrainerDTO> result = trainerFacade.findAllTrainersWithBadge(badgeDTO);
        assertEquals(result, trainersDTOList);
    }
}
