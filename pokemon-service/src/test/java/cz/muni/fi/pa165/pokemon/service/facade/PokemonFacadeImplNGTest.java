package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Tests correctness of PokemonFacade implementations
 *
 * @author Jaroslav Cechak
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class PokemonFacadeImplNGTest extends AbstractTestNGSpringContextTests {

    @Mock
    private MappingService mappingService;

    @Mock
    private PokemonService pokemonService;
    
    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private PokemonFacadeImpl pokemonFacade;

    private static List<Pokemon> PokemonList;
    private static Trainer trainer;
    private static TrainerDTO trainerDTO;
    private static Pokemon persistedPokemon;
    private static Pokemon persistedPokemon2;
    private Pokemon createdPokemon;
    private Pokemon updatedPokemon;
    private Pokemon deletedPokemon;
    private static Pokemon pokemon;
    private static PokemonDTO pokemonDTO;
    private static PokemonCreateDTO pokemonCreateDTO;
    private static List<PokemonDTO> PokemonDTOList;
    private static PokemonDTO persistedPokemonDTO;
    private static PokemonDTO persistedPokemonDTO2;

    public PokemonFacadeImplNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        PokemonList = new LinkedList<>();
        trainer = new Trainer();
        trainerDTO = new TrainerDTO();
        persistedPokemon = new Pokemon();
        persistedPokemon2 = new Pokemon();
        pokemon = new Pokemon();
        pokemonDTO = new PokemonDTO();
        pokemonCreateDTO = new PokemonCreateDTO();
        PokemonDTOList = new LinkedList<>();
        persistedPokemonDTO = new PokemonDTO();
        persistedPokemonDTO2 = new PokemonDTO();

        trainer.setId(1l);
        trainer.setName("Ask");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(new Date(System.currentTimeMillis()));
        
        trainerDTO.setId(2l);

        persistedPokemon.setId(20l);
        persistedPokemon.setName("Pika");
        persistedPokemon.setNickname("Chu");
        persistedPokemon.setSkillLevel(10);
        persistedPokemon.setTrainer(trainer);
        persistedPokemon.setType(PokemonType.ROCK);

        persistedPokemon2.setId(2l);
        persistedPokemon2.setName("Bulbasaur");
        persistedPokemon2.setNickname("B");
        persistedPokemon2.setSkillLevel(20);
        persistedPokemon2.setTrainer(trainer);
        persistedPokemon2.setType(PokemonType.GRASS);
        
        trainer.addPokemon(persistedPokemon2);
        trainer.addPokemon(persistedPokemon);

        pokemon.setId(150l);
        pokemon.setName("K");
        pokemon.setNickname("L");
        pokemon.setSkillLevel(100);
        pokemon.setTrainer(trainer);
        pokemon.setType(PokemonType.WATER);

        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setNickname(pokemon.getNickname());
        pokemonDTO.setSkillLevel(pokemon.getSkillLevel());
        pokemonDTO.setTrainerId(pokemon.getTrainer().getId());
        pokemonDTO.setType(pokemon.getType());
        
        pokemonCreateDTO.setName(pokemon.getName());
        pokemonCreateDTO.setNickname(pokemon.getNickname());
        pokemonCreateDTO.setSkillLevel(pokemon.getSkillLevel());
        pokemonCreateDTO.setTrainerId(pokemon.getTrainer().getId());
        pokemonCreateDTO.setType(pokemon.getType());

        persistedPokemonDTO.setId(persistedPokemon.getId());
        persistedPokemonDTO.setName(persistedPokemon.getName());
        persistedPokemonDTO.setNickname(persistedPokemon.getNickname());
        persistedPokemonDTO.setSkillLevel(persistedPokemon.getSkillLevel());
        persistedPokemonDTO.setTrainerId(persistedPokemon.getTrainer().getId());
        persistedPokemonDTO.setType(persistedPokemon.getType());

        persistedPokemonDTO2.setId(persistedPokemon2.getId());
        persistedPokemonDTO2.setName(persistedPokemon2.getName());
        persistedPokemonDTO2.setNickname(persistedPokemon2.getNickname());
        persistedPokemonDTO2.setSkillLevel(persistedPokemon2.getSkillLevel());
        persistedPokemonDTO2.setTrainerId(persistedPokemon2.getTrainer().getId());
        persistedPokemonDTO2.setType(persistedPokemon2.getType());

        PokemonList.add(persistedPokemon2);
        PokemonList.add(persistedPokemon);

        PokemonDTOList.add(persistedPokemonDTO2);
        PokemonDTOList.add(persistedPokemonDTO);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(pokemonService.getAllPokemons()).thenReturn(PokemonList);
        when(pokemonService.getPokemonById(persistedPokemon.getId())).thenReturn(persistedPokemon);

        doAnswer(invocation -> {
            createdPokemon = pokemon;
            createdPokemon.setId(100l);
            return null;
        }).when(pokemonService).createPokemon(pokemon);

        doAnswer(invocation -> {
            updatedPokemon = pokemon;
            return null;
        }).when(pokemonService).updatePokemon(pokemon);

        doAnswer(invocation -> {
            deletedPokemon = pokemon;
            return null;
        }).when(pokemonService).deletePokemon(pokemon);

        createdPokemon = null;
        updatedPokemon = null;
        deletedPokemon = null;
        
        when(trainerService.findTrainerById(trainer.getId())).thenReturn(trainer);

        when(mappingService.map(persistedPokemon, PokemonDTO.class)).thenReturn(persistedPokemonDTO);
        when(mappingService.map(persistedPokemon2, PokemonDTO.class)).thenReturn(persistedPokemonDTO2);
        when(mappingService.map(persistedPokemonDTO, Pokemon.class)).thenReturn(persistedPokemon);
        when(mappingService.map(persistedPokemonDTO2, Pokemon.class)).thenReturn(persistedPokemon2);
        
        when(mappingService.map(PokemonList, PokemonDTO.class)).thenReturn(PokemonDTOList);
        
        when(mappingService.map(pokemon, PokemonDTO.class)).thenReturn(pokemonDTO);
        when(mappingService.map(pokemonDTO, Pokemon.class)).thenReturn(pokemon);
        when(mappingService.map(pokemonCreateDTO, Pokemon.class)).thenReturn(pokemon);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of createPokemon method, of class PokemonFacadeImpl.
     */
    @Test
    public void testCreatePokemon() {
        PokemonDTO result = pokemonFacade.createPokemon(pokemonCreateDTO);
        
        assertEquals(result, pokemonDTO, "Created pokemon is not as expected.");
        assertEquals(result.getId(), pokemonDTO.getId(), "Created pokemon is not as expected.");
        assertEquals(result.getSkillLevel(), pokemonDTO.getSkillLevel(), "Created pokemon is not as expected.");
        assertEquals(result.getTrainerId(), pokemonDTO.getTrainerId(), "Created pokemon is not as expected.");
        assertEquals(result.getType(), pokemonDTO.getType(), "Created pokemon is not as expected.");
        
        assertNotNull(createdPokemon, "There has not been any call of method create in pokemon service.");
        assertSame(createdPokemon, pokemon, "Persisted pokemon is not the one expected.");
    }

    /**
     * Test of getPokemonById method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetPokemonById() {
        PokemonDTO result = pokemonFacade.getPokemonById(persistedPokemonDTO.getId());
        
        assertEquals(result, persistedPokemonDTO, "Did not retrieved same pokemon.");
    }

    /**
     * Test of changeSkill method, of class PokemonFacadeImpl.
     */
    @Test
    public void testChangeSkill() {
        int newSkill = pokemonDTO.getSkillLevel()+1;
        pokemonFacade.changeSkill(pokemonDTO, newSkill);
        
        assertNotNull(updatedPokemon, "There has not been any call of update method in pokemon service.");
        assertTrue(pokemonDTO.getSkillLevel() == newSkill, "Pokemon's level has not changed.");
    }

    /**
     * Test of changeTrainer method, of class PokemonFacadeImpl.
     */
    @Test
    public void testChangeTrainer() {
        pokemonFacade.changeTrainer(pokemonDTO, trainerDTO);
        
        assertEquals(pokemonDTO.getTrainerId(), trainerDTO.getId(), "Trainer id has not changed.");
        assertNotNull(updatedPokemon, "There has not been any call of update method in pokemon service.");
    }

    /**
     * Test of deletePokemon method, of class PokemonFacadeImpl.
     */
    @Test
    public void testDeletePokemon() {
        pokemonFacade.deletePokemon(pokemonDTO);
        
        assertNotNull(deletedPokemon, "There has not been any call of delete method in pokemon service.");
    }

    /**
     * Test of getAllPokemons method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetAllPokemons() {
        List<PokemonDTO> result = pokemonFacade.getAllPokemons();
        
        assertEquals(result, PokemonDTOList, "Returned list is not as expected.");
    }

    /**
     * Test of getAllPokemonsOfTrainer method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetAllPokemonsOfTrainerWithId() {
        List<PokemonDTO> result = pokemonFacade.getAllPokemonsOfTrainerWithId(trainer.getId());
        
        assertTrue(result.size() == 2, "Returned list does not contain expected number of pokemons.");
        assertEquals(result, PokemonDTOList, "Returned list does not contain expected pokemons.");
    }

    /**
     * Test of getAllPokemonsWithName method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetAllPokemonsWithName() {
        List<PokemonDTO> result = pokemonFacade.getAllPokemonsWithName("Bulbasaur");
        List<PokemonDTO> expected = new LinkedList<>();
        expected.add(persistedPokemonDTO2);
        
        assertEquals(result, expected, "Returned list is not as expected.");
    }

    /**
     * Test of getAllPokemonsWithType method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetAllPokemonsWtihType() {
        List<PokemonDTO> result = pokemonFacade.getAllPokemonsWithType(PokemonType.GRASS);
        List<PokemonDTO> expected = new LinkedList<>();
        expected.add(persistedPokemonDTO2);
        
        assertEquals(result, expected, "Returned list is not as expected.");
    }
}
