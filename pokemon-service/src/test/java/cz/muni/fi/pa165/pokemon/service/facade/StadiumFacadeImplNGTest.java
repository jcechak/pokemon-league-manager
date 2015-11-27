package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.StadiumService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Testing class for StadiumFacade.
 * @author Dominika Talianova
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class StadiumFacadeImplNGTest extends AbstractTestNGSpringContextTests {

    @InjectMocks
    private StadiumFacadeImpl stadiumFacade;

    @Mock
    private StadiumService stadiumService;

    @Mock
    private MappingService mappingService;

    private Stadium stadium1;
    private Stadium stadium2;

    private StadiumDTO stadiumDTO;
    private StadiumDTO stadium2DTO;
    private TrainerDTO trainerDTO;

    private Trainer trainer1;
    private Trainer trainer2;
    //private Trainer leader;

    private boolean called = false;

    private List<Stadium> stadiums;
    private List<Stadium> bugStadiums;

    @BeforeClass
    public void setup() throws ServiceException {

    }

    @BeforeMethod
    public void beforeMethod(){

        MockitoAnnotations.initMocks(this);

        stadium1 = new Stadium();
        trainer1 = new Trainer();

        stadium2 = new Stadium();
        trainer2 = new Trainer();


        stadium1.setId(13L);
        stadium1.setCity("Orange");
        stadium1.setType(PokemonType.BUG);

        trainer1.setDateOfBirth(Date.valueOf("1989-10-11"));
        trainer1.setSurname("Ketchum");
        trainer1.setName("Ash");
        trainer1.setId(1L);
        trainer1.setStadium(stadium1);
        stadium1.setLeader(trainer1);

        stadium2.setId(11L);
        stadium2.setCity("Azalea");
        stadium2.setType(PokemonType.FIRE);
        stadium2.setLeader(trainer2);

        List<Stadium> stadiums = new ArrayList<>();
        stadiums.add(stadium1);
        stadiums.add(stadium2);

        List<Stadium> bugStadiums = new ArrayList<>();
        bugStadiums.add(stadium1);

        stadiumDTO = TestUtils.createStadiumDTO(stadium1);
        stadium2DTO = TestUtils.createStadiumDTO(stadium2);
        trainerDTO = TestUtils.createTrainerDTO(trainer1);

        when(mappingService.map(stadiumDTO, Stadium.class)).thenReturn(stadium1);
        when(mappingService.map(stadium1, StadiumDTO.class)).thenReturn(stadiumDTO);

        when(mappingService.map(stadium2, StadiumDTO.class)).thenReturn(stadium2DTO);
        when(mappingService.map(trainer1, TrainerDTO.class)).thenReturn(trainerDTO);
        when(mappingService.map(trainerDTO, Trainer.class)).thenReturn(trainer1);


        when(stadiumService.findByCity("Orange")).thenReturn(stadium1);
        when(stadiumService.findByLeader(trainer1)).thenReturn(stadium1);
        when(stadiumService.findByType(PokemonType.BUG)).thenReturn(bugStadiums);
        when(stadiumService.getAll()).thenReturn(stadiums);
        when(stadiumService.findByLeader(trainer1)).thenReturn(stadium1);
        when(stadiumService.getStadiumById(13L)).thenReturn(stadium1);
        when(stadiumService.getTheLeader(stadium1)).thenReturn(trainer1);
        when(stadiumService.hasLeader(stadium1)).thenReturn(stadium1.getLeader() != null);

        doAnswer(invocation -> {
            called = true;
            return null;
        }).when(stadiumService).createStadium(stadium1);

        doAnswer(invocation ->{
            called = true;
            return null;
        }).when(stadiumService).deleteStadium(stadium1);

        doAnswer(invocation ->{
            called = true;
            return null;
        }).when(stadiumService).updateStadium(stadium1);
    }

    @AfterMethod
    public void afterMethod(){
        called = false;
    }

    @Test
    public void testCreateStadium(){
        stadiumFacade.createStadium(mappingService.map(stadium1, StadiumDTO.class));
        assertEquals(called, true, "Stadium was not created.");
    }

    @Test
    public void testDeleteStadium(){
        stadiumFacade.deleteStadium(mappingService.map(stadium1, StadiumDTO.class));

        assertEquals(called, true, "Stadium was not deleted.");
    }

    @Test
    public void testUpdateStadium(){
        stadiumFacade.updateStadium(mappingService.map(stadium1, StadiumDTO.class));
        assertEquals(called, true, "Stadium was not updated.");
    }

    @Test
    public void testFindById(){
        StadiumDTO tempStadium = stadiumFacade.findById(13L);
        assertEquals(tempStadium, stadiumDTO, "Found stadium does not match.");
    }

    @Test
    public void testGetAll(){
        Collection<StadiumDTO> allStadiums = stadiumFacade.findAll();
        assertEquals(allStadiums, mappingService.map(stadiums, StadiumDTO.class) ,"Failed to find all stadiums.");
    }

    @Test
    public void testFindBytType(){
        Collection<StadiumDTO> typeStadiums = stadiumFacade.findByType(PokemonType.BUG);
        assertEquals(typeStadiums, mappingService.map(bugStadiums, StadiumDTO.class), "Failed to find stadiums by type.");
    }

    @Test
    public void testFindByCity(){
        StadiumDTO cityStadium = stadiumFacade.findByCity("Orange");
        assertEquals(cityStadium, stadiumDTO, "Failed to find all stadiums by city.");
    }

    @Test
    public void testFindByLeader(){
        StadiumDTO leaderStadium = stadiumFacade
                .findStadiumByLeader(mappingService.map(trainer1, TrainerDTO.class));
        assertEquals(leaderStadium, stadiumDTO, "Failed to find stadium by leader.");
    }


    @Test
    public void testGetTheLeader(){
        StadiumDTO tempStadium = mappingService.map(stadium1, StadiumDTO.class);
        assertEquals(stadiumFacade.getTheLeader(tempStadium), mappingService.map(trainer1,TrainerDTO.class)
                ,"Failed to retrieve the leader.");
    }

    @Test
    public void testHasLeader(){

        StadiumDTO tempStadium = mappingService.map(stadium1, StadiumDTO.class);
        boolean tempCheck = stadiumFacade.hasLeader(tempStadium);
        assertTrue("The stadium does not have a stadium leader.", tempCheck);
    }


}
