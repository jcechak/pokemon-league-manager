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
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Test of proper functioning of mapper in spring context
 *
 * @author Jaroslav Cechak
 */
public class MapperIntegrationNGTest {

    private Mapper mapper;

    @Mock
    private TrainerService trainerService;

    @Mock
    private PokemonService pokemonService;

    @Mock
    private BadgeService badgeService;

    @Mock
    private StadiumService stadiumService;

    @InjectMocks
    private EntityIdCustomConverter converter;

    private static Trainer trainer;
    private static TrainerDTO trainerDTO;
    private static Pokemon pokemon;
    private static PokemonDTO pokemonDTO;
    private static Badge badge;
    private static BadgeDTO badgeDTO;
    private static Stadium stadium;
    private static StadiumDTO stadiumDTO;

    public MapperIntegrationNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        trainer = new Trainer();
        trainer.setId(1l);
        trainer.setName("Ask");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(new Date(System.currentTimeMillis()));

        trainerDTO = new TrainerDTO();
        trainerDTO.setId(trainer.getId());
        trainerDTO.setName(trainer.getName());
        trainerDTO.setSurname(trainer.getSurname());
        trainerDTO.setDateOfBirth(trainer.getDateOfBirth());

        pokemon = new Pokemon();
        pokemon.setId(1l);
        pokemon.setName("Bulbasaur");
        pokemon.setNickname("Bull");
        pokemon.setSkillLevel(20);
        pokemon.setTrainer(trainer);
        pokemon.setType(PokemonType.GRASS);

        pokemonDTO = new PokemonDTO();
        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setNickname(pokemon.getNickname());
        pokemonDTO.setSkillLevel(pokemon.getSkillLevel());
        pokemonDTO.setTrainerId(pokemon.getTrainer().getId());
        pokemonDTO.setType(pokemon.getType());

        stadium = new Stadium();
        stadium.setId(1l);
        stadium.setCity("Lavender");
        stadium.setLeader(trainer);
        stadium.setType(PokemonType.ROCK);

        stadiumDTO = new StadiumDTO();
        stadiumDTO.setId(stadium.getId());
        stadiumDTO.setCity(stadium.getCity());
        stadiumDTO.setStadiumLeaderId(stadium.getLeader().getId());
        stadiumDTO.setType(stadium.getType());

        badge = new Badge();
        badge.setId(1l);
        badge.setStadium(stadium);
        badge.setTrainer(trainer);

        badgeDTO = new BadgeDTO();
        badgeDTO.setId(badge.getId());
        badgeDTO.setStadiumId(badge.getStadium().getId());
        badgeDTO.setTrainerId(badge.getTrainer().getId());

        trainer.addPokemon(pokemon);
        trainer.addBadge(badge);

        trainerDTO.addPokemon(pokemonDTO);
        trainerDTO.addBadge(badgeDTO);
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

        List<String> mappings = new LinkedList<>();
        mappings.add("dozerMappings/pokemonMapping.xml");

        Map<String, CustomConverter> customConverters = new HashMap<>();
        customConverters.put("entityIdCustomConverter", converter);

        mapper = new DozerBeanMapper(mappings);
        ((DozerBeanMapper) mapper).setCustomConvertersWithId(customConverters);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of map method
     */
    @Test
    public void testMapPokemonToPokemonDTO() {
        PokemonDTO result = mapper.map(pokemon, PokemonDTO.class);

        assertNotNull(result, "Mapped object should not be null.");
        assertEquals(result, pokemonDTO, "Mapped object has some mismapped fields.");
        assertEquals(result.getId(), pokemonDTO.getId(), "Mapped object has some mismapped fields.");
        assertEquals(result.getSkillLevel(), pokemonDTO.getSkillLevel(), "Mapped object has some mismapped fields.");
        assertEquals(result.getTrainerId(), pokemonDTO.getTrainerId(), "Mapped object has some mismapped fields.");
        assertEquals(result.getType(), pokemonDTO.getType(), "Mapped object has some mismapped fields.");
    }

    /**
     * Test of map method
     */
    @Test
    public void testMapPokemonDTOToPokemon() {
        Pokemon result = mapper.map(pokemonDTO, Pokemon.class);
        
        assertNotNull(result, "Mapped object should not be null.");
        assertEquals(result, pokemon, "Mapped object has some mismapped fields.");
        assertEquals(result.getId(), pokemon.getId(), "Mapped object has some mismapped fields.");
        assertEquals(result.getSkillLevel(), pokemon.getSkillLevel(), "Mapped object has some mismapped fields.");
        assertEquals(result.getTrainer(), pokemon.getTrainer(), "Mapped object has some mismapped fields.");
        assertEquals(result.getType(), pokemon.getType(), "Mapped object has some mismapped fields.");
    }

    /**
     * Test of map method
     */
    @Test
    public void testMapBadgeToBadgeDTO() {
        BadgeDTO result = mapper.map(badge, BadgeDTO.class);

        assertNotNull(result, "Mapped object should not be null.");
        assertEquals(result, badgeDTO, "Mapped object has some mismapped fields.");
        assertEquals(result.getId(), badgeDTO.getId(), "Mapped object has some mismapped fields.");
    }

    /**
     * Test of map method
     */
    @Test
    public void testMapBadgeDTOToBadge() {
        Badge result = mapper.map(badgeDTO, Badge.class);

        assertNotNull(result, "Mapped object should not be null.");
        assertEquals(result, badge, "Mapped object has some mismapped fields.");
        assertEquals(result.getId(), badge.getId(), "Mapped object has some mismapped fields.");
    }
    
    /**
     * Test of map method
     */
    @Test
    public void testMapStadiumToStadiumDTO() {
        StadiumDTO result = mapper.map(stadium, StadiumDTO.class);

        assertNotNull(result, "Mapped object should not be null.");
        assertEquals(result, stadiumDTO, "Mapped object has some mismapped fields.");
        assertEquals(result.getId(), stadiumDTO.getId(), "Mapped object has some mismapped fields.");
    }

    /**
     * Test of map method
     */
    @Test
    public void testMapStadiumDTOToStadium() {
        Stadium result = mapper.map(stadiumDTO, Stadium.class);

        assertNotNull(result, "Mapped object should not be null.");
        assertEquals(result, stadium, "Mapped object has some mismapped fields.");
        assertEquals(result.getId(), stadium.getId(), "Mapped object has some mismapped fields.");
    }
    
    /**
     * Test of map method
     */
    @Test
    public void testMapTrainerToTrainerDTO() {
        TrainerDTO result = mapper.map(trainer, TrainerDTO.class);
        
        assertEquals(result, trainerDTO, "Mapped object has some mismapped fields.");
        assertEquals(result.getId(), trainerDTO.getId(), "Mapped object has some mismapped fields.");
        assertEquals(result.getPokemons(), trainerDTO.getPokemons(), "Mapped object has some mismapped fields.");
        assertEquals(result.getBadges(), trainerDTO.getBadges(), "Mapped object has some mismapped fields.");
        assertEquals(result.getStadium(), trainerDTO.getStadium(), "Mapped object has some mismapped fields.");
    }

    /**
     * Test of map method
     */
    @Test
    public void testMapTrainerDTOToTrainer() {
        Trainer result = mapper.map(trainerDTO, Trainer.class);
        
        assertEquals(result, trainer, "Mapped object has some mismapped fields.");
        assertEquals(result.getId(), trainer.getId(), "Mapped object has some mismapped fields.");
        assertEquals(result.getPokemons(), trainer.getPokemons(), "Mapped object has some mismapped fields.");
        assertEquals(result.getBadges(), trainer.getBadges(), "Mapped object has some mismapped fields.");
        assertEquals(result.getStadium(), trainer.getStadium(), "Mapped object has some mismapped fields.");
    }
}

