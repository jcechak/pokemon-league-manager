package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.context.ServiceConfiguration;
import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.service.BadgeService;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.StadiumService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
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

    @Mock
    private TrainerService trainerService;

    @Mock
    private StadiumService stadiumService;

    @InjectMocks
    private BadgeFacadeImpl badgeFacade;

    private static BadgeDTO badgeDTO;
    private static BadgeDTO badgeDTO2;

    private Badge createdBadge;

    private static Trainer trainerLeader;
    private static Trainer trainer2;
    private static Stadium stadium;

    private static Badge badge;
    private static Badge badge2;
    private static List<Badge> badges = new ArrayList<>();
    private static List<BadgeDTO> badgesDTO = new ArrayList<>();

    @BeforeClass
    public void setUpClass() throws Exception {
        MockitoAnnotations.initMocks(this);

        trainerLeader = new Trainer();
        trainerLeader.setId(1l);
        trainerLeader.setName("Prd");
        trainerLeader.setSurname("Makovy");
        trainerLeader.setDateOfBirth(Date.valueOf("1989-07-08"));


        trainer2 = new Trainer();
        trainer2.setId(2l);
        trainer2.setName("Ask");
        trainer2.setSurname("Ketchum");
        trainer2.setDateOfBirth(Date.valueOf("1992-04-15"));

        stadium = new Stadium();
        stadium.setId(666l);
        stadium.setCity("Ostrava");
        stadium.setType(PokemonType.NORMAL);
        stadium.setLeader(trainerLeader);

        badge = new Badge();
        badge.setId(42l);
        badge.setTrainer(trainerLeader);
        badge.setStadium(stadium);

        badge2 = new Badge();
        badge2.setId(54l);
        badge2.setTrainer(trainerLeader);
        badge2.setStadium(stadium);

        badges.add(badge);
        badges.add(badge2);

        badgeDTO = new BadgeDTO();
        badgeDTO.setStadiumId(badge.getStadium().getId());
        badgeDTO.setTrainerId(badge.getTrainer().getId());
        badgeDTO.setId(badge.getId());

        badgeDTO2 = new BadgeDTO();
        badgeDTO2.setStadiumId(badge2.getStadium().getId());
        badgeDTO2.setTrainerId(badge2.getTrainer().getId());
        badgeDTO2.setId(badge2.getId());

        badgesDTO.add(badgeDTO);
        badgesDTO.add(badgeDTO2);

    }

    @BeforeMethod
    public void setUpMethod() throws Exception {

        doAnswer(invocation -> {
            createdBadge = badge;
            createdBadge.setId(100l);
            return null;
        }).when(badgeService).createBadge(badge);

        when(badgeService.findBadgeById(badge.getId())).thenReturn(badge);
        when(badgeService.getAllBadges()).thenReturn(badges);

        when(mappingService.map(badges, BadgeDTO.class)).thenReturn(badgesDTO);

        when(mappingService.map(badgeDTO, Badge.class)).thenReturn(badge);
        when(mappingService.map(badge, BadgeDTO.class)).thenReturn(badgeDTO);
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
    public void testGetAllBadges() throws Exception {
        Collection<BadgeDTO> badgeList = badgeFacade.getAllBadges();
        assertTrue(badgeList.size() == badgesDTO.size(), "List does not contain expected number of badges, found: " + badgeList.size());
        assertEquals(badgeList, badgesDTO, "Returned list does not contain expected pokemons.");
    }

    @Test
    public void testGetBadgesWithTrainer() throws Exception {
        //todo?
    }

    @Test
    public void testGetBadgesWithStadium() throws Exception {
       //todo?
    }

    @Test
    public void testFindBadgeWithTrainerAndStadium() throws Exception {
        //todo?
    }

}
