package cz.muni.fi.pa165.pokemon.service;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import cz.muni.fi.pa165.pokemon.dao.StadiumDao;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Testing class for StadiumService
 * @author Dominika Talianova
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class StadiumServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private StadiumDao stadiumDao;

    @Mock
    private Stadium stadium1;

    @Mock
    private Stadium stadium2;

    @Mock
    private Trainer trainer1;

    @Mock
    private Trainer trainer2;

    @Autowired
    @InjectMocks
    private StadiumService stadiumService;


    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void beforeMethod(){

        stadium1 = new Stadium();
        stadium2 = new Stadium();
        trainer1 = new Trainer();
        List<Stadium> stadiums = new ArrayList<>();
        List<Stadium> electricStadiums = new ArrayList<>();

        stadium1.setCity("Orange");
        stadium1.setType(PokemonType.FIRE);
        stadium1.setLeader(trainer1);
        stadium1.setId(13L);


        stadium2.setCity("Azalea");
        stadium2.setType(PokemonType.ELECTRIC);
        stadium2.setLeader(trainer2);
        stadium2.setId(12L);

        trainer1.setId(1L);
        trainer1.setName("Ash");
        trainer1.setSurname("Ketchum");
        trainer1.setDateOfBirth(Date.valueOf("1993-10-14"));
        trainer1.setStadium(stadium1);

        stadiums.add(stadium1);
        stadiums.add(stadium2);

        electricStadiums.add(stadium2);


        when(stadiumDao.findById(13L)).thenReturn(stadium1);
        when(stadiumDao.findByCity("Orange")).thenReturn(stadium1);
        when(stadiumDao.findAll()).thenReturn(stadiums);
        when(stadiumDao.findByPokemonType(PokemonType.ELECTRIC)).thenReturn(electricStadiums);
        when(stadiumDao.findByStadiumLeader(trainer1)).thenReturn(stadium1);
    }

    @Test
    public void testGetStadiumById(){
        Stadium tempStadium = stadiumDao.findById(13L);

        assertEquals(stadium1, tempStadium, "Failed to find stadium by ID.");
    }

    @Test
    public void testGetAll(){
        List<Stadium> foundStadiums = stadiumDao.findAll();
        if(foundStadiums.size()!=2){
            fail("Returned list has " + foundStadiums.size() + " items.");
        } else if (!foundStadiums.contains(stadium1) || !foundStadiums.contains(stadium2)) {
            fail("The list does not contain expected items.");
        }
    }

    @Test
    public void testFindByType(){
        List<Stadium> foundStadiums = stadiumDao.findByPokemonType(PokemonType.ELECTRIC);

        if(foundStadiums.size()!= 1){
            fail("Returned list has " +foundStadiums.size() + " items.");
        } else if (foundStadiums.contains(stadium1)){
            fail("The list does not contain correct itmes.");
        }
    }

    @Test
    public void testFindByCity(){
        Stadium tempStadium = stadiumDao.findByCity("Orange");
        assertEquals(tempStadium, stadium1, "Found stadium does not equal to the original one.");
    }

    @Test
    public void testFindByLeader(){
        Stadium tempStadium = stadiumDao.findByStadiumLeader(trainer1);
        assertEquals(tempStadium, stadium1, "Found stadium does not match.");
    }

    @Test
    public void testGetLeaderInfo(){
        String leaderInfo = stadiumService.getLeaderInfo(trainer1);
        String trainer1Info = "Trainer{"
                + "id=" + "1"
                + ", name=" + "Ash"
                + ", surname=" + "Ketchum"
                + ", dateOfBirth=" + "1993-10-14"
                + '}';
        assertEquals(leaderInfo,trainer1Info, "The trainer information does not match. TrainerInfo: |"+trainer1Info+"|" +
                "leaderInfo:|"+leaderInfo);
    }

    @Test
    public void testGetLeader(){
        Trainer tempTrainer = stadiumService.getTheLeader(stadium1);
        assertEquals(tempTrainer,trainer1, "Found trainer does not match.");
    }

    @Test
    public void testHasLeader(){
        boolean tempCheck = stadiumService.hasLeader(stadium1);
        if(!tempCheck){
            fail("Failed to check if the stadium has a leader.");
        }
    }
}
