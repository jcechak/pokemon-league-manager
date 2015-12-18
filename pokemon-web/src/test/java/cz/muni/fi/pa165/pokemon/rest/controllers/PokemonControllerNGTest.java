package cz.muni.fi.pa165.pokemon.rest.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test for rest controller.
 *
 * @author Jaroslav Cechak
 */
@WebAppConfiguration
public class PokemonControllerNGTest {

    @Mock
    private PokemonFacade pokemonFacade;

    @InjectMocks
    private PokemonRestController pokemonController;

    private MockMvc mockMvc;

    private static PokemonCreateDTO pCreate;
    private static PokemonDTO p1;
    private static PokemonDTO p2;
    private static PokemonDTO p3;

    private static List<PokemonDTO> all;
    private static List<PokemonDTO> specificList;

    public PokemonControllerNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        pCreate = new PokemonCreateDTO();
        pCreate.setName("Jmeno");
        pCreate.setNickname("Nick");
        pCreate.setSkillLevel(20);
        pCreate.setTrainerId(20l);
        pCreate.setType(PokemonType.ROCK);

        p1 = new PokemonDTO();
        p1.setId(1l);
        p1.setName("Pokemon1");
        p1.setNickname("p1");
        p1.setSkillLevel(1);
        p1.setTrainerId(1l);
        p1.setType(PokemonType.BUG);

        p2 = new PokemonDTO();
        p2.setId(2l);
        p2.setName("name");
        p2.setNickname("p2");
        p2.setSkillLevel(2);
        p2.setTrainerId(2l);
        p2.setType(PokemonType.ROCK);

        p3 = new PokemonDTO();
        p3.setId(3l);
        p3.setName("name");
        p3.setNickname("p3");
        p3.setSkillLevel(3);
        p3.setTrainerId(2l);
        p3.setType(PokemonType.ROCK);

        all = Arrays.asList(p1, p2, p3);

        specificList = Arrays.asList(p2, p3);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pokemonController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();

        doReturn(p1).when(pokemonFacade).getPokemonById(p1.getId());
        doReturn(p2).when(pokemonFacade).getPokemonById(p2.getId());
        doReturn(p3).when(pokemonFacade).getPokemonById(p3.getId());
        doReturn(all).when(pokemonFacade).getAllPokemons();
        doReturn(specificList).when(pokemonFacade).getAllPokemonsOfTrainerWithId(p2.getTrainerId());
        doReturn(specificList).when(pokemonFacade).getAllPokemonsWithName(p2.getName());
        doReturn(specificList).when(pokemonFacade).getAllPokemonsWithType(p2.getType());

    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of createPokemon method, of class PokemonRestController.
     */
    @Test
    public void testCreatePokemon() throws Exception {
        doReturn(p1.getId()).when(pokemonFacade).createPokemon(pCreate);

        mockMvc.perform(
                post("/rest/pokemons/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJsonBytes(pCreate)))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonBytes(p1)));

    }
    
    @Test
    public void testCreateNonValid() throws Exception {
        PokemonCreateDTO wrong = new PokemonCreateDTO();
        
        // in reality, facade would throw an exception
        doReturn(1234l).when(pokemonFacade).createPokemon(wrong);

        mockMvc.perform(
                post("/rest/pokemons/create")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJsonBytes(wrong)))
                .andExpect(status().isNotAcceptable())
                .andExpect(status().reason("Invalid parameters."));
    }

    /**
     * Test of changeSkill method, of class PokemonRestController.
     */
    @Test
    public void testChangeSkill() throws Exception {
        int oldSkill = p1.getSkillLevel();
        doAnswer(invocation -> {
            p1.setSkillLevel(p1.getSkillLevel() + 1);
            return null;
        }).when(pokemonFacade).changeSkill(p1.getId(), p1.getSkillLevel() + 1);

        mockMvc.perform(
                post("/rest/pokemons/changeskill/" + p1.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJsonBytes(p1.getSkillLevel() + 1)))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonBytes(p1)))
                .andExpect(jsonPath("$.skillLevel").value(oldSkill + 1));
    }

    /**
     * Test of changeTrainer method, of class PokemonRestController.
     */
    @Test
    public void testChangeTrainer() throws Exception {
        Long oldTrainer = p1.getTrainerId();
        doAnswer(invocation -> {
            p1.setTrainerId(p1.getTrainerId() + 1);
            return null;
        }).when(pokemonFacade).changeTrainer(p1.getTrainerId(), p1.getTrainerId() + 1);

        mockMvc.perform(
                post("/rest/pokemons/changetrainer/" + p1.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJsonBytes(p1.getTrainerId() + 1)))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonBytes(p1)))
                .andExpect(jsonPath("$.trainerId").value(((Long)(oldTrainer + 1)).intValue()));
    }

    /**
     * Test of tradePokemons method, of class PokemonRestController.
     */
    @Test
    public void testTradePokemons() throws Exception {
        Long oldTrainer1 = p1.getTrainerId();
        Long oldTrainer2 = p2.getTrainerId();
        doAnswer(invocation -> {
            p1.setTrainerId(oldTrainer2);
            p2.setTrainerId(oldTrainer1);
            return null;
        }).when(pokemonFacade).tradePokemon(p1.getId(), p2.getId());

        mockMvc.perform(
                post("/rest/pokemons/trade/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(convertObjectToJsonBytes(Arrays.asList(p1.getId(), p2.getId()))))
                .andExpect(status().isOk());

        assertEquals(p1.getTrainerId(), oldTrainer2, "Trainer id has not changed or chagned to some unexpected value.");
        assertEquals(p2.getTrainerId(), oldTrainer1, "Trainer id has not changed or chagned to some unexpected value.");
    }

    /**
     * Test of deletePokemon method, of class PokemonRestController.
     */
    @Test
    public void testDeletePokemon() throws Exception {
        final List<Long> deletedPokemon = new LinkedList<>();
        doAnswer(invocation -> {
            deletedPokemon.add(p1.getId());
            return null;
        }).when(pokemonFacade).deletePokemon(p1.getId());

        mockMvc.perform(
                delete("/rest/pokemons/" + p1.getId()))
                .andExpect(status().isOk());

        assertTrue(deletedPokemon.size() == 1, "There was no delete called.");
        assertEquals(deletedPokemon.get(0), p1.getId(), "Did not deleted the correct pokemon.");
    }

    /**
     * Test of getAllPokemons method, of class PokemonRestController.
     */
    @Test
    public void testGetAllPokemons() throws Exception {
        mockMvc.perform(
                get("/rest/pokemons"))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonBytes(all)));
    }

    /**
     * Test of getPokemonOfTrainer method, of class PokemonRestController.
     */
    @Test
    public void testGetPokemonOfTrainer() throws Exception {
        mockMvc.perform(
                post("/rest/pokemons/withtrainer")
                .content(convertObjectToJsonBytes(p2.getTrainerId()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonBytes(specificList)));
    }

    /**
     * Test of getPokemonWithName method, of class PokemonRestController.
     */
    @Test
    public void testGetPokemonWithName() throws Exception {
        mockMvc.perform(
                post("/rest/pokemons/withname")
                .content(convertObjectToJsonBytes(p2.getName()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonBytes(specificList)));
    }

    /**
     * Test of getPokemonWithType method, of class PokemonRestController.
     */
    @Test
    public void testGetPokemonWithType() throws Exception {
        mockMvc.perform(
                post("/rest/pokemons/withtype")
                .content(convertObjectToJsonBytes(p2.getType()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonBytes(specificList)));
    }

    /**
     * Test of getPokemon method, of class PokemonRestController.
     */
    @Test
    public void testGetPokemon() throws Exception {
        mockMvc.perform(
                get("/rest/pokemons/" + p1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonBytes(p1)));
    }

    private static String convertObjectToJsonBytes(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

}
