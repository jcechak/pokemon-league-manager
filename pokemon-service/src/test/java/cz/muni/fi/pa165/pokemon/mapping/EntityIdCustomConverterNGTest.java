package cz.muni.fi.pa165.pokemon.mapping;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.service.BadgeService;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
import cz.muni.fi.pa165.pokemon.service.StadiumService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import java.sql.Date;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test of proper id to entity and vice verca conversion.
 *
 * @author Jaroslav Cechak
 */
public class EntityIdCustomConverterNGTest {

    @Mock
    private TrainerService trainerService;

    @Mock
    private PokemonService pokemonService;

    @Mock
    private BadgeService badgeService;

    @Mock
    private StadiumService stadiumService;

    @InjectMocks
    private EntityIdCustomConverter convertor;

    private static Trainer trainer;
    private static Pokemon pokemon;
    private static Badge badge;
    private static Stadium stadium;

    public EntityIdCustomConverterNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        trainer = new Trainer();
        trainer.setId(1l);
        trainer.setName("Ask");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(new Date(System.currentTimeMillis()));

        pokemon = new Pokemon();
        pokemon.setId(1l);
        pokemon.setName("Bulbasaur");
        pokemon.setNickname("Bull");
        pokemon.setSkillLevel(20);
        pokemon.setTrainer(trainer);
        pokemon.setType(PokemonType.GRASS);

        stadium = new Stadium();
        stadium.setId(1l);
        stadium.setCity("Lavender");
        stadium.setLeader(trainer);
        stadium.setType(PokemonType.ROCK);

        badge = new Badge();
        badge.setId(1l);
        badge.setStadium(stadium);
        badge.setTrainer(trainer);

        trainer.addPokemon(pokemon);
        trainer.addBadge(badge);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(trainerService.findTrainerById(trainer.getId())).thenReturn(trainer);
        when(pokemonService.getPokemonById(pokemon.getId())).thenReturn(pokemon);
        when(badgeService.findBadgeById(badge.getId())).thenReturn(badge);
        when(stadiumService.getStadiumById(stadium.getId())).thenReturn(stadium);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of convert method, of class EntityIdCustomConverter.
     */
    @Test
    public void testConvertTrainer() {
        Object result = convertor.convert(null, trainer.getId(), Trainer.class, Long.class);

        assertEquals(result.getClass(), Trainer.class, "Converted object is not of a Trainer class.");
        assertSame(result, trainer, "Converted object is not the one expected.");
    }

    /**
     * Test of convert method, of class EntityIdCustomConverter.
     */
    @Test
    public void testConvertPokemon() {
        Object result = convertor.convert(null, pokemon.getId(), Pokemon.class, Long.class);

        assertEquals(result.getClass(), Pokemon.class, "Converted object is not of a Pokemon class.");
        assertSame(result, pokemon, "Converted object is not the one expected.");
    }

    /**
     * Test of convert method, of class EntityIdCustomConverter.
     */
    @Test
    public void testConvertBadge() {
        Object result = convertor.convert(null, badge.getId(), Badge.class, Long.class);

        assertEquals(result.getClass(), Badge.class, "Converted object is not of a Badge class.");
        assertSame(result, badge, "Converted object is not the one expected.");
    }

    /**
     * Test of convert method, of class EntityIdCustomConverter.
     */
    @Test
    public void testConvertStadium() {
        Object result = convertor.convert(null, stadium.getId(), Stadium.class, Long.class);

        assertEquals(result.getClass(), Stadium.class, "Converted object is not of a Stadium class.");
        assertSame(result, stadium, "Converted object is not the one expected.");
    }
}
