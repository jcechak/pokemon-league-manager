package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.dozer.Mapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * Test of proper functioning of mapping service
 *
 * @author Jaroslav Cechak
 */
public class MappingServiceTest {

    @InjectMocks
    private MappingServiceImpl mappingService;

    @Mock
    private Mapper mapper;

    private static Set<Pokemon> pokemonSet;
    private static List<Pokemon> pokemonList;
    private static Trainer trainer;
    private static Pokemon pokemon;
    private static Pokemon pokemon2;
    private static List<PokemonDTO> pokemonDTOList;
    private static PokemonDTO pokemonDTO;
    private static PokemonDTO pokemonDTO2;
    
    public MappingServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        pokemonSet = new HashSet<>();
        pokemonList = new LinkedList<>();
        trainer = new Trainer();
        pokemon = new Pokemon();
        pokemon2 = new Pokemon();
        pokemonDTOList = new LinkedList<>();
        pokemonDTO = new PokemonDTO();
        pokemonDTO2 = new PokemonDTO();

        trainer = new Trainer();
        trainer.setId(1l);
        trainer.setName("Ask");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(new Date(System.currentTimeMillis()));    

        pokemon.setId(20l);
        pokemon.setName("Pika");
        pokemon.setNickname("Chu");
        pokemon.setSkillLevel(10);
        pokemon.setTrainer(trainer);
        pokemon.setType(PokemonType.ROCK);

        pokemon2.setId(2l);
        pokemon2.setName("Bulbasaur");
        pokemon2.setNickname("B");
        pokemon2.setSkillLevel(20);
        pokemon2.setTrainer(trainer);
        pokemon2.setType(PokemonType.GRASS);

        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setNickname(pokemon.getNickname());
        pokemonDTO.setSkillLevel(pokemon.getSkillLevel());
        pokemonDTO.setTrainerId(pokemon.getTrainer().getId());
        pokemonDTO.setType(pokemon.getType());

        pokemonDTO2.setId(pokemon2.getId());
        pokemonDTO2.setName(pokemon2.getName());
        pokemonDTO2.setNickname(pokemon2.getNickname());
        pokemonDTO2.setSkillLevel(pokemon2.getSkillLevel());
        pokemonDTO2.setTrainerId(pokemon2.getTrainer().getId());
        pokemonDTO2.setType(pokemon2.getType());

        pokemonSet.add(pokemon);
        pokemonSet.add(pokemon2);

        pokemonList.add(pokemon2);
        pokemonList.add(pokemon);

        pokemonDTOList.add(pokemonDTO2);
        pokemonDTOList.add(pokemonDTO);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        when(mapper.map(pokemon, PokemonDTO.class)).thenReturn(pokemonDTO);
        when(mapper.map(pokemon2, PokemonDTO.class)).thenReturn(pokemonDTO2);
        when(mapper.map(pokemonDTO2, Pokemon.class)).thenReturn(pokemon2);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void testInjection() {
        assertNotNull(mappingService, "Service has not been injected. Check annotations.");
    }

    /**
     * Test of map method, of class MappingServiceImpl.
     */
    @Test
    public void testMap_Collection_Class() {
        List<PokemonDTO> result = mappingService.map(pokemonSet, PokemonDTO.class);

        assertTrue(result.size() == 2, "Mapped set does not contains all items.");
        assertTrue(result.containsAll(pokemonDTOList), "Mapped set does not contains expected items.");

        result = mappingService.map(pokemonList, PokemonDTO.class);
        assertTrue(result.size() == 2, "Mapped list does not contains all items.");
        assertTrue(result.containsAll(pokemonDTOList), "Mapped list does not contains expected items.");
        assertEquals(result, pokemonDTOList, "Mapped list does not contain objects in the same order.");
    }

    /**
     * Test of map method, of class MappingServiceImpl.
     */
    @Test
    public void testMap_Object_Class() {
        PokemonDTO result = mappingService.map(pokemon, PokemonDTO.class);

        assertEquals(result, pokemonDTO, "Object has not been mapped correctly.");

        Pokemon result2 = mappingService.map(pokemonDTO2, Pokemon.class);
        
        assertEquals(result2, pokemon2, "Object has not been mapped correctly");
    }

    /**
     * Test of getMapper method, of class MappingServiceImpl.
     */
    @Test
    public void testGetMapper() {
        assertNotNull(mappingService.getMapper(), "Returned mapper is null.");
        assertSame(mappingService.getMapper(), mapper, "Returned mapper is not the same as the one in the context.");
    }

}
