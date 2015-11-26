package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
    private final PokemonFacade pokemonFacade = new PokemonFacadeImpl();

    private static Trainer trainer1;
    private static Trainer trainer2;

    private static List<Pokemon> pokemonList;
    private static List<Pokemon> pokemonListByName;
    private static List<Pokemon> pokemonListByType;

    private static Pokemon persistedPokemon1;
    private static Pokemon persistedPokemon2;
    private static Pokemon newPokemon;

    private static PokemonDTO persistedPokemonDTO1;
    private static PokemonDTO persistedPokemonDTO2;
    private static PokemonDTO pokemonDTO;
    private static List<PokemonDTO> pokemonDTOList;
    private static List<PokemonDTO> pokemonDTOListByName;
    private static List<PokemonDTO> pokemonDTOListByType;

    private static PokemonCreateDTO pokemonCreateDTO;

    private static Object[] arguments;
    private static List<Object> expectedArguments;

    public PokemonFacadeImplNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        trainer1 = new Trainer();
        trainer2 = new Trainer();

        persistedPokemon1 = new Pokemon();
        persistedPokemon2 = new Pokemon();
        newPokemon = new Pokemon();

        pokemonList = new LinkedList<>();
        pokemonListByName = new LinkedList<>();
        pokemonListByType = new LinkedList<>();

        persistedPokemonDTO1 = new PokemonDTO();
        persistedPokemonDTO2 = new PokemonDTO();
        pokemonDTO = new PokemonDTO();

        pokemonDTOList = new LinkedList<>();
        pokemonDTOListByName = new LinkedList<>();
        pokemonDTOListByType = new LinkedList<>();

        pokemonCreateDTO = new PokemonCreateDTO();

        trainer1.setId(1l);
        trainer1.setName("Ask");
        trainer1.setSurname("Ketchum");
        trainer1.setDateOfBirth(new Date(System.currentTimeMillis()));

        trainer2.setId(2l);
        trainer2.setName("Brock");
        trainer2.setSurname("Surname");
        trainer2.setDateOfBirth(new Date(System.currentTimeMillis()));

        persistedPokemon1.setId(1l);
        persistedPokemon1.setName("Pika");
        persistedPokemon1.setNickname("Chu");
        persistedPokemon1.setSkillLevel(10);
        persistedPokemon1.setTrainer(trainer1);
        persistedPokemon1.setType(PokemonType.ROCK);

        persistedPokemon2.setId(2l);
        persistedPokemon2.setName("Bulbasaur");
        persistedPokemon2.setNickname("B");
        persistedPokemon2.setSkillLevel(20);
        persistedPokemon2.setTrainer(trainer1);
        persistedPokemon2.setType(PokemonType.GRASS);

        trainer1.addPokemon(persistedPokemon2);
        trainer1.addPokemon(persistedPokemon1);

        newPokemon.setId(150l);
        newPokemon.setName("K");
        newPokemon.setNickname("L");
        newPokemon.setSkillLevel(100);
        newPokemon.setTrainer(trainer1);
        newPokemon.setType(PokemonType.WATER);

        pokemonList.add(persistedPokemon2);
        pokemonList.add(persistedPokemon1);

        pokemonListByName.add(persistedPokemon1);

        pokemonListByType.add(persistedPokemon2);

        pokemonDTO.setId(newPokemon.getId());
        pokemonDTO.setName(newPokemon.getName());
        pokemonDTO.setNickname(newPokemon.getNickname());
        pokemonDTO.setSkillLevel(newPokemon.getSkillLevel());
        pokemonDTO.setTrainerId(newPokemon.getTrainer().getId());
        pokemonDTO.setType(newPokemon.getType());

        persistedPokemonDTO1.setId(persistedPokemon1.getId());
        persistedPokemonDTO1.setName(persistedPokemon1.getName());
        persistedPokemonDTO1.setNickname(persistedPokemon1.getNickname());
        persistedPokemonDTO1.setSkillLevel(persistedPokemon1.getSkillLevel());
        persistedPokemonDTO1.setTrainerId(persistedPokemon1.getTrainer().getId());
        persistedPokemonDTO1.setType(persistedPokemon1.getType());

        persistedPokemonDTO2.setId(persistedPokemon2.getId());
        persistedPokemonDTO2.setName(persistedPokemon2.getName());
        persistedPokemonDTO2.setNickname(persistedPokemon2.getNickname());
        persistedPokemonDTO2.setSkillLevel(persistedPokemon2.getSkillLevel());
        persistedPokemonDTO2.setTrainerId(persistedPokemon2.getTrainer().getId());
        persistedPokemonDTO2.setType(persistedPokemon2.getType());

        pokemonDTOList.add(persistedPokemonDTO2);
        pokemonDTOList.add(persistedPokemonDTO1);
        
        pokemonDTOListByName.add(persistedPokemonDTO1);
        
        pokemonDTOListByType.add(persistedPokemonDTO2);

        pokemonCreateDTO.setName(newPokemon.getName());
        pokemonCreateDTO.setNickname(newPokemon.getNickname());
        pokemonCreateDTO.setSkillLevel(newPokemon.getSkillLevel());
        pokemonCreateDTO.setTrainerId(newPokemon.getTrainer().getId());
        pokemonCreateDTO.setType(newPokemon.getType());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        arguments = null;
        expectedArguments = new LinkedList<>();

        MockitoAnnotations.initMocks(this);

        when(pokemonService.getPokemonById(persistedPokemon1.getId())).thenReturn(persistedPokemon1);
        when(pokemonService.getPokemonById(persistedPokemon2.getId())).thenReturn(persistedPokemon2);
        when(pokemonService.getAllPokemons()).thenReturn(pokemonList);
        when(pokemonService.getAllPokemonsOfTrainer(trainer1)).thenReturn(trainer1.getPokemons());
        when(pokemonService.getAllPokemonsWithName(persistedPokemon1.getName())).thenReturn(pokemonListByName);
        when(pokemonService.getAllPokemonsWithType(persistedPokemon2.getType())).thenReturn(pokemonListByType);

        // save parameters and return id
        when(pokemonService.createPokemon(newPokemon)).thenAnswer(
                (InvocationOnMock invocation) -> {
                    arguments = invocation.getArguments();
                    return newPokemon.getId();
                }
        );

        Answer<Object> saveParameters = (InvocationOnMock invocation) -> {
            arguments = invocation.getArguments();
            return null;
        };

        doAnswer(saveParameters).when(pokemonService).changeTrainer(persistedPokemon1, trainer2);
        doAnswer(saveParameters).when(pokemonService).updatePokemon(persistedPokemon1);
        doAnswer(saveParameters).when(pokemonService).deletePokemon(persistedPokemon1);
        doAnswer(saveParameters).when(pokemonService).changeSkill(persistedPokemon1, 20);
        doAnswer(saveParameters).when(pokemonService).tradePokemon(persistedPokemon1, persistedPokemon2);

        when(trainerService.findTrainerById(trainer1.getId())).thenReturn(trainer1);

        when(mappingService.map(persistedPokemon1, PokemonDTO.class)).thenReturn(persistedPokemonDTO1);
        when(mappingService.map(persistedPokemon2, PokemonDTO.class)).thenReturn(persistedPokemonDTO2);

        when(mappingService.map(pokemonList, PokemonDTO.class)).thenReturn(pokemonDTOList);
        when(mappingService.map(pokemonListByName, PokemonDTO.class)).thenReturn(pokemonDTOListByName);
        when(mappingService.map(pokemonListByType, PokemonDTO.class)).thenReturn(pokemonDTOListByType);
        
        when(mappingService.map(pokemonCreateDTO, Pokemon.class)).thenReturn(newPokemon);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of createPokemon method, of class PokemonFacadeImpl.
     */
    @Test
    public void testCreatePokemon() {
        Long id = pokemonFacade.createPokemon(pokemonCreateDTO);

        expectedArguments.add(newPokemon);

        assertEquals((Object) id, newPokemon.getId(), "The returned id is not the same as the one of created entity.");
        assertEquals(arguments, expectedArguments, "The underlying service layer did not received correct arguments.");
    }

    /**
     * Test of getPokemonById method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetPokemonById() {
        PokemonDTO result = pokemonFacade.getPokemonById(persistedPokemonDTO1.getId());

        assertEquals(result, persistedPokemonDTO1, "Did not retrieved same pokemon.");
    }

    /**
     * Test of changeSkill method, of class PokemonFacadeImpl.
     */
    @Test
    public void testChangeSkill() {
        pokemonFacade.changeSkill(persistedPokemon1.getId(), 20);

        expectedArguments.add(persistedPokemon1);
        expectedArguments.add(20);

        assertNotNull(arguments, "The corresponding method of service layer has not been called.");
        assertEquals(arguments, expectedArguments, "The underlying service layer did not received correct arguments.");
    }

    /**
     * Test of changeTrainer method, of class PokemonFacadeImpl.
     */
    @Test
    public void testChangeTrainer() {
        pokemonFacade.changeTrainer(persistedPokemon1.getId(), trainer2.getId());

        expectedArguments.add(persistedPokemon1);
        expectedArguments.add(trainer1);

        assertNotNull(arguments, "The corresponding method of service layer has not been called.");
        assertEquals(arguments, expectedArguments, "The underlying service layer did not received correct arguments.");
    }

    /**
     * Test of deletePokemon method, of class PokemonFacadeImpl.
     */
    @Test
    public void testDeletePokemon() {
        pokemonFacade.deletePokemon(persistedPokemon1.getId());

        expectedArguments.add(persistedPokemon1);

        assertNotNull(arguments, "The corresponding method of service layer has not been called.");
        assertEquals(arguments, expectedArguments, "The underlying service layer did not received correct arguments.");
    }

    /**
     * Test of getAllPokemons method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetAllPokemons() {
        List<PokemonDTO> result = pokemonFacade.getAllPokemons();

        assertNotNull(result, "Returned list should not be null.");
        assertTrue(result.size() == 2, "Returned list does not contain expected number of pokemons.");
        assertEquals(result, pokemonDTOList, "Returned list is not as expected.");
    }

    /**
     * Test of getAllPokemonsOfTrainer method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetAllPokemonsOfTrainerWithId() {
        List<PokemonDTO> result = pokemonFacade.getAllPokemonsOfTrainerWithId(trainer1.getId());

        assertNotNull(result, "Returned list should not be null.");
        assertTrue(result.size() == 2, "Returned list does not contain expected number of pokemons.");
        assertEquals(result, pokemonDTOList, "Returned list does not contain expected pokemons.");
    }

    /**
     * Test of getAllPokemonsWithName method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetAllPokemonsWithName() {
        List<PokemonDTO> result = pokemonFacade.getAllPokemonsWithName(persistedPokemon1.getName());

        assertNotNull(result, "Returned list should not be null.");
        assertTrue(result.size() == 1, "Returned list does not contain expected number of pokemons.");
        assertEquals(result, pokemonListByName, "Returned list is not as expected.");
    }

    /**
     * Test of getAllPokemonsWithType method, of class PokemonFacadeImpl.
     */
    @Test
    public void testGetAllPokemonsWtihType() {
        List<PokemonDTO> result = pokemonFacade.getAllPokemonsWithType(persistedPokemon2.getType());

        assertNotNull(result, "Returned list should not be null.");
        assertTrue(result.size() == 1, "Returned list does not contain expected number of pokemons.");
        assertEquals(result, pokemonListByType, "Returned list is not as expected.");
    }

    /**
     * Test of tradePokemon method, of class PokemonFacadeImpl.
     */
    @Test
    public void testTradePokemon() {
        pokemonFacade.tradePokemon(persistedPokemon1.getId(), persistedPokemon2.getId());

        expectedArguments.add(persistedPokemon1);
        expectedArguments.add(persistedPokemon2);

        assertNotNull(arguments, "The corresponding method of service layer has not been called.");
        assertEquals(arguments, expectedArguments, "The underlying service layer did not received correct arguments.");
    }
}
