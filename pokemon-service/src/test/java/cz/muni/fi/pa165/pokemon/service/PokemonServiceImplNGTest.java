package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.PokemonDao;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.*;

import java.sql.Date;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.testng.Assert.*;

/**
 * Test of pokemon service implementation
 *
 * @author Jaroslav Cechak
 */
public class PokemonServiceImplNGTest {

    @Mock
    private PokemonDao pokemonDao;

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    private static Pokemon pokemon1;
    private static Pokemon pokemon2;

    private static Trainer trainer1;
    private static Trainer trainer2;

    private static List<Pokemon> pokemonList;

    private Object[] arguments;
    private List<Object> expectedArguments;

    public PokemonServiceImplNGTest() {
    }

    public static void prepareTestData() {
        pokemon1 = new Pokemon();
        pokemon1.setId(1l);
        pokemon1.setName("Pikachu");
        pokemon1.setNickname("Pika");
        pokemon1.setSkillLevel(10);
        pokemon1.setTrainer(trainer1);
        pokemon1.setType(PokemonType.ELECTRIC);

        pokemon2 = new Pokemon();
        pokemon2.setId(2l);
        pokemon2.setName("Some");
        pokemon2.setNickname("Pokemon");
        pokemon2.setSkillLevel(20);
        pokemon2.setTrainer(trainer1);
        pokemon2.setType(PokemonType.ROCK);

        pokemonList = new LinkedList<>();
        pokemonList.add(pokemon1);
        pokemonList.add(pokemon2);

        trainer1 = new Trainer();
        trainer1.setId(1l);
        trainer1.setName("Ask");
        trainer1.setSurname("Ketchum");
        trainer1.setDateOfBirth(new Date(System.currentTimeMillis()));
        trainer1.addPokemon(pokemon1);
        trainer1.addPokemon(pokemon2);

        trainer2 = new Trainer();
        trainer2.setId(2l);
        trainer2.setName("Garry");
        trainer2.setSurname("Oak");
        trainer2.setDateOfBirth(new Date(System.currentTimeMillis()));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        arguments = null;
        expectedArguments = new LinkedList<>();

        prepareTestData();

        MockitoAnnotations.initMocks(this);

        Answer<Object> saveParameters = (InvocationOnMock invocation) -> {
            arguments = invocation.getArguments();
            return null;
        };

        doAnswer(saveParameters).when(pokemonDao).update(any(Pokemon.class));
        doAnswer(saveParameters).when(pokemonDao).delete(any(Pokemon.class));

        doAnswer(invocation -> {
            arguments = invocation.getArguments();
            invocation.getArgumentAt(0, Pokemon.class).setId(1234l);
            return null;
        }).when(pokemonDao).create(any(Pokemon.class));

        doAnswer(invocation -> {
            Long id = invocation.getArgumentAt(0, Long.class);
            if (id == pokemon1.getId()) {
                return pokemon1;
            } else {
                return pokemon2;
            }
        }).when(pokemonDao).findById(any(Long.class));

        when(pokemonDao.findAll()).thenReturn(Collections.unmodifiableList(pokemonList));

        when(trainerService.findTrainerById(trainer1.getId())).thenReturn(trainer1);
        when(trainerService.findTrainerById(trainer2.getId())).thenReturn(trainer2);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of createPokemon method, of class PokemonServiceImpl.
     */
    @Test
    public void testCreatePokemon() {
        pokemon1.setId(null);
        Long newId = pokemonService.createPokemon(pokemon1);

        expectedArguments.add(pokemon1);

        assertNotNull(arguments, "The corresponding method of service layer has not been called.");
        assertEquals(arguments, expectedArguments.toArray(), "The underlying service layer did not received correct arguments.");
        assertEquals(newId, (Long) 1234l, "Returned id is not the one that has been given to new pokemon.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testCreateNull() {
        pokemonService.createPokemon(null);
        fail("Pokemon service should have thrown IllegalArgumentException when creating null entity.");
    }

    /**
     * Test of updatePokemon method, of class PokemonServiceImpl.
     */
    @Test
    public void testUpdatePokemon() {
        pokemon1.setName("x");
        pokemonService.updatePokemon(pokemon1);

        expectedArguments.add(pokemon1);

        assertNotNull(arguments, "The corresponding method of service layer has not been called.");
        assertEquals(arguments, expectedArguments.toArray(), "The underlying service layer did not received correct arguments.");

        pokemon1.setName("Pikachu");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testUpdateNull() {
        pokemonService.updatePokemon(null);
        fail("Pokemon service should have thrown IllegalArgumentException when updating null entity.");
    }

    /**
     * Test of deletePokemon method, of class PokemonServiceImpl.
     */
    @Test
    public void testDeletePokemon() {
        pokemonService.deletePokemon(pokemon1);

        expectedArguments.add(pokemon1);

        assertNotNull(arguments, "The corresponding method of service layer has not been called.");
        assertEquals(arguments, expectedArguments.toArray(), "The underlying service layer did not received correct arguments.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDeleteNull() {
        pokemonService.deletePokemon(null);
        fail("Pokemon service should have thrown IllegalArgumentException when deleting null entity.");
    }

    /**
     * Test of getPokemonById method, of class PokemonServiceImpl.
     */
    @Test
    public void testGetPokemonById() {
        Pokemon result = pokemonService.getPokemonById(pokemon1.getId());

        assertEquals(result, pokemon1, "Returned object is not the one expected.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetByNullId() {
        pokemonService.getPokemonById(null);
        fail("Pokemon service should have thrown IllegalArgumentException when given null as entity id.");
    }

    /**
     * Test of getAllPokemons method, of class PokemonServiceImpl.
     */
    @Test
    public void testGetAllPokemons() {
        List<Pokemon> result = pokemonService.getAllPokemons();

        assertTrue(result.size() == 2, "Returned lost has unexpected size.");
        assertTrue(result.containsAll(pokemonList), "List does not contain expected objects.");
        assertEquals(result, pokemonList, "List does not contain objects expected order.");
    }

    /**
     * Test of changeSkill method, of class PokemonServiceImpl.
     */
    @Test
    public void testChangeSkill() {
        pokemonService.changeSkill(pokemon1, 20);

        expectedArguments.add(pokemon1);

        assertNotNull(arguments, "The corresponding method of service layer has not been called.");
        assertEquals(arguments, expectedArguments.toArray(), "The underlying service layer did not received correct arguments.");
        assertEquals(pokemon1.getSkillLevel(), 20, "Pokemon's skill level has not changed.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeSkillNull() {
        pokemonService.changeSkill(null, 20);
        fail("Pokemon service should have thrown IllegalArgumentException when given null as a pokemon.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeSkillNegative() {
        pokemonService.changeSkill(pokemon1, -1);
        fail("Pokemon service should have thrown IllegalArgumentException when given negative number as skill level.");
    }

    /**
     * Test of changeTrainer method, of class PokemonServiceImpl.
     */
    @Test
    public void testChangeTrainer() {
        pokemonService.changeTrainer(pokemon1, trainer2);

        expectedArguments.add(pokemon1);
        assertNotNull(arguments, "The corresponding method of service layer has not been called.");
        assertEquals(arguments, expectedArguments.toArray(), "The underlying service layer did not received correct arguments.");

        assertEquals(pokemon1.getTrainer(), trainer2, "Pokemon has not changed.");
        assertTrue(trainer2.getPokemons().contains(pokemon1), "Pokemon has not been added to new trainer.");
        assertFalse(trainer1.getPokemons().contains(pokemon1), "Pokemon has not been removed from previous trainer.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeTrainerNull() {
        pokemonService.changeTrainer(null, trainer1);
        fail("Pokemon service should have thrown IllegalArgumentException when given null as a pokemon.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testChangeTrainerNullTrainer() {
        pokemonService.changeTrainer(pokemon1, null);
        fail("Pokemon service should have thrown IllegalArgumentException when given null as a trainer.");
    }

    /**
     * Test of tradePokemon method, of class PokemonServiceImpl.
     */
    @Test
    public void testTradePokemon() {
        pokemonService.changeTrainer(pokemon2, trainer2);

        pokemonService.tradePokemon(pokemon1, pokemon2);

        assertEquals(pokemon1.getTrainer(), trainer2, "Pokemon has not changed.");
        assertEquals(pokemon2.getTrainer(), trainer1, "Pokemon has not changed.");
        assertTrue(trainer2.getPokemons().contains(pokemon1), "Pokemon has not been added to new trainer.");
        assertFalse(trainer1.getPokemons().contains(pokemon1), "Pokemon has not been removed from previous trainer.");
        assertTrue(trainer1.getPokemons().contains(pokemon2), "Pokemon has not been added to new trainer.");
        assertFalse(trainer2.getPokemons().contains(pokemon2), "Pokemon has not been removed from previous trainer.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTradeNull1() {
        pokemonService.tradePokemon(null, pokemon2);
        fail("Pokemon service should have thrown IllegalArgumentException when given null as one of pokemons to trade.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTradeNull2() {
        pokemonService.tradePokemon(pokemon2, null);
        fail("Pokemon service should have thrown IllegalArgumentException when given null as one of pokemons to trade.");
    }

    /**
     * Test of getAllPokemonsOfTrainer method, of class PokemonServiceImpl.
     */
    @Test
    public void testGetAllPokemonsOfTrainer() {
        List<Pokemon> result = pokemonService.getAllPokemonsOfTrainer(trainer1);

        assertEquals(result, pokemonList, "Returned list of pokemons is not as expected.");

        result = pokemonService.getAllPokemonsOfTrainer(trainer2);

        assertTrue(result.isEmpty(), "Returned list of pokemons is not empty.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetByNullTrainer() {
        pokemonService.getAllPokemonsOfTrainer(null);
        fail("Pokemon service should have thrown IllegalArgumentException when given null as trainer.");
    }

    /**
     * Test of getAllPokemonsWithName method, of class PokemonServiceImpl.
     */
    @Test
    public void testGetAllPokemonsWithName() {
        pokemonList.remove(pokemon1);

        List<Pokemon> result = pokemonService.getAllPokemonsWithName(pokemon2.getName());

        assertEquals(result, pokemonList, "Returned list of pokemons is not as expected.");

        pokemonList.remove(pokemon2);
        pokemonList.add(pokemon1);

        result = pokemonService.getAllPokemonsWithName(pokemon1.getName());

        assertEquals(result, pokemonList, "Returned list of pokemons is not as expected.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetByNullName() {
        pokemonService.getAllPokemonsWithName(null);
        fail("Pokemon service should have thrown IllegalArgumentException when given null as name.");
    }

    /**
     * Test of getAllPokemonsWithType method, of class PokemonServiceImpl.
     */
    @Test
    public void testGetAllPokemonsWithType() {
        pokemonList.remove(pokemon1);

        List<Pokemon> result = pokemonService.getAllPokemonsWithType(pokemon2.getType());

        assertEquals(result, pokemonList, "Returned list of pokemons is not as expected.");

        pokemonList.remove(pokemon2);
        pokemonList.add(pokemon1);

        result = pokemonService.getAllPokemonsWithType(pokemon1.getType());

        assertEquals(result, pokemonList, "Returned list of pokemons is not as expected.");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetByNullType() {
        pokemonService.getAllPokemonsWithType(null);
        fail("Pokemon service should have thrown IllegalArgumentException when given null as pokemon type.");
    }

}
