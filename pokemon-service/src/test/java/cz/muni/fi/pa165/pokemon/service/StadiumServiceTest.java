package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.StadiumDao;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Testing class for StadiumService
 *
 * @author Dominika Talianova
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class StadiumServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private StadiumDao stadiumDao;

    private Stadium stadium1;

    private Stadium stadium2;

    private Trainer trainer1;

    private Trainer trainer2;
    private boolean called = false;

    @Autowired
    @InjectMocks
    private StadiumService stadiumService;

    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void beforeMethod() {

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
        when(stadiumDao.findAll()).thenReturn(Collections.unmodifiableList(stadiums));
        when(stadiumDao.findByPokemonType(PokemonType.ELECTRIC)).thenReturn(Collections.unmodifiableList(electricStadiums));
        when(stadiumDao.findByStadiumLeader(trainer1)).thenReturn(stadium1);
        doAnswer(invocation -> {
            called = true;
            return null;
        }).when(stadiumDao).create(stadium1);
        doAnswer(invocation -> {
            called = true;
            return null;
        }).when(stadiumDao).delete(stadium1);
    }

    @AfterMethod
    public void afterMethod() {
        called = false;
    }

    @Test
    public void testCreateStadium() {
        stadiumService.createStadium(stadium1);

        assertEquals(called, true, "TEST createStadium - No dao method called.");
    }

    @Test
    public void testDeleteStadium() {
        stadiumService.deleteStadium(stadium1);

        assertEquals(called, true, "TEST deleteStadium - No dao method called.");
    }

    @Test
    public void testGetStadiumById() {
        Stadium tempStadium = stadiumDao.findById(13L);

        assertEquals(tempStadium, stadium1, "Found stadium does not match.");
    }

    @Test
    public void testGetAll() {
        List<Stadium> foundStadiums = stadiumDao.findAll();
        assertEquals(foundStadiums.size(), 2, "Returned list has " + foundStadiums.size() + " items.");
        assertTrue(foundStadiums.contains(stadium1), "The list does not contain expected items.");
        assertTrue(foundStadiums.contains(stadium2), "The list does not contain expected items.");
    }

    @Test
    public void testFindByType() {
        List<Stadium> foundStadiums = stadiumDao.findByPokemonType(PokemonType.ELECTRIC);

        assertEquals(foundStadiums.size(), 1, "Returned list has " + foundStadiums.size() + " items.");
        assertFalse(foundStadiums.contains(stadium1), "The list does not contain correct items.");
        assertTrue(foundStadiums.contains(stadium2), "The list does not contain correct items.");
    }

    @Test
    public void testFindByCity() {
        Stadium tempStadium = stadiumDao.findByCity("Orange");
        assertEquals(tempStadium, stadium1, "Found stadium does not equal to the original one.");
    }

    @Test
    public void testFindByLeader() {
        Stadium tempStadium = stadiumDao.findByStadiumLeader(trainer1);
        assertEquals(tempStadium, stadium1, "Found stadium does not match.");
    }

    @Test
    public void testGetLeaderInfo() {
        String leaderInfo = stadiumService.getLeaderInfo(trainer1);
        String trainer1Info = "Trainer{"
                + "id=" + "1"
                + ", name=" + "Ash"
                + ", surname=" + "Ketchum"
                + ", dateOfBirth=" + "1993-10-14"
                + '}';
        assertEquals(leaderInfo, trainer1Info, "The trainer information does not match. TrainerInfo: |" + trainer1Info + "|"
                + "leaderInfo:|" + leaderInfo);
    }

    @Test
    public void testGetLeader() {
        Trainer tempTrainer = stadiumService.getTheLeader(stadium1);
        assertEquals(tempTrainer, trainer1, "Found trainer does not match.");
    }

    @Test
    public void testHasLeader() {
        boolean tempCheck = stadiumService.hasLeader(stadium1);
        if (!tempCheck) {
            fail("The stadium does not have a leader.");
        }
    }
}
