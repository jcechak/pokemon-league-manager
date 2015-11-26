package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.PokemonDao;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jaroslav Cechak
 */
public class PokemonServiceImplNGTest {

    @Mock
    private PokemonDao pokemonDao;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    private static Pokemon p1;
    private static Pokemon p2;

    private static Trainer t1;

    private static List<Pokemon> l1;

    private Pokemon passedThrough;
    private boolean createCalled;
    private boolean updateCalled;
    private boolean deleteCalled;

    public PokemonServiceImplNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        t1 = new Trainer();
        t1.setId(1l);
        t1.setName("Ask");
        t1.setSurname("Ketchum");
        t1.setDateOfBirth(new Date(System.currentTimeMillis()));

        p1 = new Pokemon();
        p1.setId(1l);
        p1.setName("Pikachu");
        p1.setNickname("Pika");
        p1.setSkillLevel(10);
        p1.setTrainer(t1);
        p1.setType(PokemonType.ELECTRIC);

        p2 = new Pokemon();
        p2.setId(2l);
        p2.setName("Some");
        p2.setNickname("Pokemon");
        p2.setSkillLevel(20);
        p2.setTrainer(t1);
        p2.setType(PokemonType.ROCK);

        l1 = new LinkedList<>();
        l1.add(p1);
        l1.add(p2);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        createCalled = false;
        updateCalled = false;
        deleteCalled = false;

        passedThrough = null;

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                updateCalled = true;
                passedThrough = invocation.getArgumentAt(0, Pokemon.class);
                return null;
            }
        }).when(pokemonDao).update(any(Pokemon.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                createCalled = true;
                passedThrough = invocation.getArgumentAt(0, Pokemon.class);
                return null;
            }
        }).when(pokemonDao).create(any(Pokemon.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                deleteCalled = true;
                passedThrough = invocation.getArgumentAt(0, Pokemon.class);
                return null;
            }
        }).when(pokemonDao).delete(any(Pokemon.class));

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Long id = invocation.getArgumentAt(0, Long.class);
                if (id == p1.getId()) {
                    return p1;
                } else {
                    return p2;
                }
            }
        }).when(pokemonDao).findById(any(Long.class));

        when(pokemonDao.findAll()).thenReturn(l1);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of createPokemon method, of class PokemonServiceImpl.
     */
    @Test
    public void testCreatePokemon() {
        pokemonService.createPokemon(p1);
        
        assertTrue(createCalled, "Service did not called create method of DAO");
        assertEquals(passedThrough, p1, "Object passed to DAO for persisting has been changed.");
    }

    /**
     * Test of updatePokemon method, of class PokemonServiceImpl.
     */
    @Test
    public void testUpdatePokemon() {
        pokemonService.updatePokemon(p1);
        
        assertTrue(updateCalled, "Service did not called update method of DAO.");
        assertEquals(passedThrough, p1, "Object passed to DAO for update has been changed.");
    }

    /**
     * Test of deletePokemon method, of class PokemonServiceImpl.
     */
    @Test
    public void testDeletePokemon() {
        pokemonService.deletePokemon(p1);
        
        assertTrue(deleteCalled, "Service did not called delete method of DAO.");
        assertEquals(passedThrough, p1, "Object passed to DAO for deletion has been changed.");
    }

    /**
     * Test of getPokemonById method, of class PokemonServiceImpl.
     */
    @Test
    public void testGetPokemonById() {
        Pokemon result = pokemonService.getPokemonById(p1.getId());
        
        assertEquals(result, p1, "Returned object is not the one expected.");
    }

    /**
     * Test of getAllPokemons method, of class PokemonServiceImpl.
     */
    @Test
    public void testGetAllPokemons() {
        List<Pokemon> result = pokemonService.getAllPokemons();
        
        assertTrue(result.size() == 2, "Returned lost has unexpected size.");
        assertTrue(result.containsAll(l1), "List does not contain expected objects.");
        assertEquals(result, l1, "List does not contain objects expected order.");
    }

}
