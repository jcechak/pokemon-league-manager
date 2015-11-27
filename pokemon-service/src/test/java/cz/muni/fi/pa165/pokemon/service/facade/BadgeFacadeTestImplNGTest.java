package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.context.ServiceConfiguration;
import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.service.BadgeService;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Test correctness of {@link BadgeFacade} implementation.
 * @author Marek Sabo
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BadgeFacadeTestImplNGTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MappingService mappingService;

    @Mock
    private BadgeService badgeService;

    @InjectMocks
    private BadgeFacadeImpl badgeFacade;

    private static BadgeDTO badgeDTO;

    private Badge createdBadge;
    private Badge updatedBadge;

    private static Trainer trainer;
    private static Trainer anotherTrainer;
    private static Stadium stadium;

    private static TrainerDTO anotherTrainerDTO;
    private static StadiumDTO stadiumDTO;

    private static Badge badge;
    private static Badge anotherBadge;

    private static List<Badge> badges = new ArrayList<>();
    private static List<BadgeDTO> badgesDTO = new ArrayList<>();

    private static List<Badge> badgesTrainer = new ArrayList<>();
    private static List<Badge> badgesStadium = new ArrayList<>();

    @BeforeClass
    public void setUpClass() throws Exception {
        MockitoAnnotations.initMocks(this);

        Trainer trainerLeader = new Trainer();
        trainerLeader.setId(1l);
        trainerLeader.setName("Prd");
        trainerLeader.setSurname("Makovy");
        trainerLeader.setDateOfBirth(Date.valueOf("1989-07-08"));

        trainer = new Trainer();
        trainer.setId(2l);
        trainer.setName("Ask");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(Date.valueOf("1992-04-15"));

        anotherTrainer = new Trainer();
        anotherTrainer.setId(17L);
        anotherTrainer.setName("Bruck");
        anotherTrainer.setSurname("Ruck");
        anotherTrainer.setDateOfBirth(Date.valueOf("1999-07-05"));

        anotherTrainerDTO = TestUtils.createTrainerDTO(anotherTrainer);

        stadium = new Stadium();
        stadium.setId(666l);
        stadium.setCity("Ostrava");
        stadium.setType(PokemonType.NORMAL);
        stadium.setLeader(trainerLeader);

        stadiumDTO = TestUtils.createStadiumDTO(stadium);

        anotherBadge = new Badge();
        anotherBadge.setId(54l);
        anotherBadge.setTrainer(anotherTrainer);
        anotherBadge.setStadium(stadium);

        badgesStadium.add(anotherBadge);

        BadgeDTO anotherBadgeDTO = TestUtils.createBadgeDTO(anotherBadge);

        badgesDTO.add(badgeDTO);
        badgesDTO.add(anotherBadgeDTO);

        badgesTrainer.add(anotherBadge);
        badgesStadium.add(anotherBadge);
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {

        badge = new Badge();
        badge.setId(42l);
        badge.setTrainer(trainer);
        badge.setStadium(stadium);

        badges.clear();
        badges.add(anotherBadge);
        badges.add(badge);


        badgeDTO = TestUtils.createBadgeDTO(badge);

        doAnswer(invocation -> {
            createdBadge = badge;
            createdBadge.setId(100l);
            return null;
        }).when(badgeService).createBadge(badge);

        when(badgeService.findBadgeById(badge.getId())).thenReturn(badge);
        when(badgeService.getAllBadges()).thenReturn(badges);
        when(badgeService.getBadgesWithTrainer(anotherTrainer)).thenReturn(badgesTrainer);
        when(badgeService.getBadgesWithStadium(stadium)).thenReturn(badgesStadium);
        when(badgeService.findBadgeByTrainerAndStadium(anotherTrainer, stadium)).thenReturn(anotherBadge);

        doAnswer(invocation -> {
            updatedBadge = badge;
            return null;
        }).when(badgeService).updateBadge(badge);

        when(mappingService.map(badges, BadgeDTO.class)).thenReturn(badgesDTO);
        when(mappingService.map(badgesTrainer, BadgeDTO.class)).thenReturn(TestUtils.createBadgeDTOList(badgesTrainer));
        when(mappingService.map(badgesStadium, BadgeDTO.class)).thenReturn(TestUtils.createBadgeDTOList(badgesStadium));

        when(mappingService.map(badgeDTO, Badge.class)).thenReturn(badge);
        when(mappingService.map(badge, BadgeDTO.class)).thenReturn(badgeDTO);

        when(mappingService.map(anotherBadge, BadgeDTO.class)).thenReturn(TestUtils.createBadgeDTO(anotherBadge));

        when(mappingService.map(anotherTrainerDTO, Trainer.class)).thenReturn(anotherTrainer);
        when(mappingService.map(stadiumDTO, Stadium.class)).thenReturn(stadium);
    }

    @Test
    public void testAssignBadge() throws Exception {
        badgeFacade.assignBadge(badgeDTO);

        assertNotNull(createdBadge, "There has not been any call of method create in badge service.");
        assertSame(createdBadge, badge, "Persisted badge is not the one expected.");
    }

    @Test
    public void testFindBadgeById() throws Exception {
        BadgeDTO result = badgeFacade.findBadgeById(badgeDTO.getId());
        assertEquals(result, badgeDTO, "Did not retrieved the same badge.");
    }

    @Test
    public void testRemoveBadge() throws Exception {
        badgeFacade.removeBadge(badgeDTO);
        assertNull(badgeService.findBadgeById(badgeDTO.getId()), "Badge was not deleted properly");
    }

    @Test
    public void testUpdateBadge() throws Exception {
        Long oldTrainerId = badgeDTO.getTrainerId();
        Long newTrainerId = 41357L;
        badgeDTO.setTrainerId(newTrainerId);
        badgeFacade.updateBadge(badgeDTO);
        assertEquals(badgeService.findBadgeById(badgeDTO.getId()), updatedBadge, "Badge update not working");

        badgeDTO.setTrainerId(oldTrainerId);
        badgeFacade.updateBadge(badgeDTO);
        assertEquals(badgeService.findBadgeById(badgeDTO.getId()), updatedBadge, "Badge update not working");
    }

    @Test
    public void testGetAllBadges() throws Exception {
        Collection<BadgeDTO> badgeList = badgeFacade.getAllBadges();
        assertTrue(badgeList.size() == badgesDTO.size(), "List does not contain expected number of badges, found: " + badgeList.size());
        assertEquals(badgeList, badgesDTO, "Returned list does not contain expected badges.");
    }

    @Test
    public void testGetBadgesWithTrainer() throws Exception {
        List<BadgeDTO> badgeList = badgeFacade.getBadgesWithTrainer(anotherTrainerDTO);
        assertTrue(badgeList.size() == badgesTrainer.size(), "List does not contain expected number of badges, found: " + badgeList.size());
        assertEquals(badgeList, TestUtils.createBadgeDTOList(badgesTrainer), "Returned list does not contain expected badges.");
    }

    @Test
    public void testGetBadgesWithStadium() throws Exception {
        List<BadgeDTO> badgeList = badgeFacade.getBadgesWithStadium(stadiumDTO);
        assertTrue(badgeList.size() == badgesStadium.size(), "List does not contain expected number of badges, found: " + badgeList.size());
        assertEquals(badgeList, TestUtils.createBadgeDTOList(badgesStadium), "Returned list does not contain expected badges.");
    }

    @Test
    public void testFindBadgeWithTrainerAndStadium() throws Exception {
        BadgeDTO badgeDTO = badgeFacade.findBadgeWithTrainerAndStadium(TestUtils.createTrainerDTO(anotherTrainer),
                TestUtils.createStadiumDTO(stadium));
        assertEquals(badgeDTO, TestUtils.createBadgeDTO(anotherBadge), "Badge are not the same.");
    }

}
