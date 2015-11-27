package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.exceptions.PokemonServiceException;
import cz.muni.fi.pa165.pokemon.context.ServiceConfiguration;
import cz.muni.fi.pa165.pokemon.dao.BadgeDao;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * These tests verify that {@link BadgeService} is correctly implemented.
 * @author Marek Sabo
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BadgeServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private BadgeDao badgeDao;

    private Badge badge;

    private Badge anotherBadge;

    private Badge createdBadge;
    private List<Badge> badges = new ArrayList<>();

    @Inject
    @InjectMocks
    private BadgeService badgeService;

    private static Trainer trainer;
    private static Trainer anotherTrainer;
    private static Trainer trainerLeader;
    private static Stadium stadium;

    @BeforeClass
    public void setUp() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        trainerLeader = new Trainer();
        trainerLeader.setId(1L);
        trainerLeader.setName("Prd");
        trainerLeader.setSurname("Makovy");
        trainerLeader.setDateOfBirth(Date.valueOf("1989-07-08"));

        trainer = new Trainer();
        trainer.setId(2L);
        trainer.setName("Ask");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(Date.valueOf("1992-04-15"));

        anotherTrainer = new Trainer();
        anotherTrainer.setId(17L);
        anotherTrainer.setName("Bruck");
        anotherTrainer.setSurname("Ruck");
        anotherTrainer.setDateOfBirth(Date.valueOf("1999-07-05"));

        stadium = new Stadium();
        stadium.setId(666L);
        stadium.setCity("Ostrava");
        stadium.setType(PokemonType.NORMAL);
        stadium.setLeader(trainerLeader);
        trainerLeader.setStadium(stadium);

        anotherBadge = new Badge();
        anotherBadge.setId(888L);
        anotherBadge.setTrainer(anotherTrainer);
        anotherBadge.setStadium(stadium);

        badge = new Badge();
        badge.setId(456L);
        badge.setTrainer(trainer);
        badge.setStadium(stadium);

        badges.add(badge);
        badges.add(anotherBadge);
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {


        when(badgeDao.findById(badge.getId())).thenReturn(badge);
        when(badgeDao.findAll()).thenReturn(Collections.unmodifiableList(badges));
        when(badgeDao.findAllWithStadium(stadium)).thenReturn(Collections.unmodifiableList(badges));
        when(badgeDao.findByTrainerAndStadium(trainer, stadium)).thenReturn(badge);
        when(badgeDao.findByTrainerAndStadium(anotherTrainer, stadium)).thenReturn(anotherBadge);

        List<Badge> trainerList = new ArrayList<>();
        trainerList.add(badge);
        when(badgeDao.findAllWithTrainer(trainer)).thenReturn(Collections.unmodifiableList(trainerList));

        doAnswer(invocation -> {
            createdBadge = badge;
            return null;
        }).when(badgeDao).create(badge);

        doAnswer(invocation -> {
            badge.setId(null);
            return null;
        }).when(badgeDao).delete(badge);
    }

    @Test
    public void testFindBadgeById() throws Exception {
        Badge foundBadge = badgeService.findBadgeById(badge.getId());
        assertSame(badge, foundBadge, "Failed to find badge");
    }

    @Test(expectedExceptions = PokemonServiceException.class)
    public void testCreateBadgeToLeader() throws Exception {
        Badge wrongBadge = new Badge();
        wrongBadge.setTrainer(trainerLeader);
        wrongBadge.setStadium(stadium);
        badgeService.createBadge(wrongBadge);
    }

    @Test
    public void testCreateBadge() throws Exception {
        badgeService.createBadge(badge);
        assertNotNull(badge.getId(), "Badge id is null");
        assertSame(badge, createdBadge, "Badges are different");
    }

    @Test
    public void testUpdateBadge() throws Exception {

        badge.setTrainer(anotherTrainer);
        badgeService.updateBadge(badge);
        Badge resultBadge =  badgeService.findBadgeById(badge.getId());
        assertSame(resultBadge.getTrainer(), anotherTrainer, "Update of trainer is not working");
        assertSame(resultBadge, badge, "Update of trainer is not working");

        String oldCity = badge.getStadium().getCity();
        String newCity = "Rome";
        badge.getStadium().setCity(newCity);
        badgeService.updateBadge(badge);
        resultBadge =  badgeService.findBadgeById(badge.getId());
        assertEquals(resultBadge.getStadium().getCity(), newCity, "Update of city name not working");
        assertEquals(resultBadge, badge, "Update of city name not working");

        badge.setTrainer(trainer);
        badge.getStadium().setCity(oldCity);
        badgeService.updateBadge(badge);
        resultBadge =  badgeService.findBadgeById(badge.getId());
        assertEquals(resultBadge, badge, "Update of multiple attributes not working");

    }

    @Test
    public void testDeleteBadge() throws Exception {
        badgeService.deleteBadge(badge);
        assertNull(badge.getId(), "Badge ID is not null after operation delete.");
    }

    @Test
    public void testGetAllBadges() throws Exception {
        List<Badge> resultList = badgeService.getAllBadges();
        assertTrue(resultList.size() == badges.size(), "Found incorrect number of badges: " + resultList.size());
        assertEquals(resultList, badges, "Lists are not same");
    }

    @Test
    public void testGetBadgesWithTrainer() throws Exception {
        List<Badge> resultList = badgeService.getBadgesWithTrainer(trainer);
        assertTrue(resultList.size() == 1, "Found incorrect number of badges: " + resultList.size());
        assertTrue(resultList.contains(badge), "Lists are not same");
    }

    @Test
    public void testGetBadgesWithStadium() throws Exception {
        List<Badge> resultList = badgeService.getBadgesWithStadium(stadium);
        assertTrue(resultList.size() == badges.size(), "Found incorrect number of badges: " + resultList.size());
        assertEquals(resultList, badges, "Lists are not same");
    }

    @Test
    public void testFindBadgeByTrainerAndStadium() throws Exception {
        Badge result = badgeService.findBadgeByTrainerAndStadium(trainer, stadium);
        assertSame(result, badge, "Badges are not same");
        result = badgeService.findBadgeByTrainerAndStadium(anotherTrainer, stadium);
        assertSame(result, anotherBadge, "Badges are not same");
    }
}
