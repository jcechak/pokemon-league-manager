package cz.muni.fi.pa165.pokemon.mapping;

import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
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
 * Test of proper functioning of mapping service
 *
 * @author Jaroslav Cechak
 */
public class MapperNGTest {

    private static Mapper mapper;
    
    @Mock
    private static EntityIdCustomConverter trainerIdCustomConverter;

    private static Trainer trainer;
    private static Pokemon pokemon;
    private static PokemonDTO pokemonDTO;
    
    public MapperNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        trainer = new Trainer();
        pokemon = new Pokemon();
        pokemonDTO = new PokemonDTO();

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

        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setNickname(pokemon.getNickname());
        pokemonDTO.setSkillLevel(pokemon.getSkillLevel());
        pokemonDTO.setTrainerId(pokemon.getTrainer().getId());
        pokemonDTO.setType(pokemon.getType());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        List<String> mappings = new LinkedList<>();
        mappings.add("dozerMappings/pokemonMapping.xml");        

        Map<String, CustomConverter> customConverters = new HashMap<>();
        customConverters.put("trainerIdCustomConverter", trainerIdCustomConverter);
        
        mapper = new DozerBeanMapper(mappings);
        ((DozerBeanMapper) mapper).setCustomConvertersWithId(customConverters);
                
        when(trainerIdCustomConverter.convert(null, trainer.getId(), Trainer.class, Long.class)).thenReturn(trainer);
        when(trainerIdCustomConverter.convert(null, trainer, Long.class, Trainer.class)).thenReturn(trainer.getId());
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of map method
     */
    @Test
    public void testMap_Pokemon_Class() {
        PokemonDTO result = mapper.map(pokemon, PokemonDTO.class);        
        assertEquals(result, pokemonDTO, "Mapped object is not as expected.");
    }

    /**
     * Test of map method
     */
    @Test
    public void testMap_PokemonDTO_Class() {
        Pokemon result = mapper.map(pokemonDTO, Pokemon.class);
        assertEquals(result, pokemon, "Mapped object is not as expected.");
    }
}
