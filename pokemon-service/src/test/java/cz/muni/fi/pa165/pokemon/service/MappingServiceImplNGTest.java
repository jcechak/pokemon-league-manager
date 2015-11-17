package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.mapping.TrainerIdCustomConverter;
import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.dozer.Mapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class MappingServiceImplNGTest extends AbstractTestNGSpringContextTests {

    @Inject
    private MappingServiceImpl mappingService;
    
    private static TrainerService trainerService;

    @Inject
    private Mapper mapper;

    private static Set<Pokemon> testCollection1;
    private static List<Pokemon> testCollection2;
    private static Trainer trainer;
    private static Pokemon testObject;
    private static Pokemon testObject2;
    private static List<PokemonDTO> controlList;
    private static PokemonDTO controlObject;
    private static PokemonDTO controlObject2;

    @Inject
    public void setTranerService(TrainerService ts) {
        MappingServiceImplNGTest.trainerService = ts;
    }    
    
    public MappingServiceImplNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        testCollection1 = new HashSet<>();
        testCollection2 = new LinkedList<>();
        trainer = new Trainer();
        testObject = new Pokemon();
        testObject2 = new Pokemon();
        controlList = new LinkedList<>();
        controlObject = new PokemonDTO();
        controlObject2 = new PokemonDTO();

        trainer = new Trainer();
        trainer.setName("Ask");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(new Date(System.currentTimeMillis()));
        trainerService.createTrainer(trainer);        

        testObject.setId(20l);
        testObject.setName("Pika");
        testObject.setNickname("Chu");
        testObject.setSkillLevel(10);
        testObject.setTrainer(trainer);
        testObject.setType(PokemonType.ROCK);

        testObject2.setId(2l);
        testObject2.setName("Bulbasaur");
        testObject2.setNickname("B");
        testObject2.setSkillLevel(20);
        testObject2.setTrainer(trainer);
        testObject2.setType(PokemonType.GRASS);

        controlObject.setId(testObject.getId());
        controlObject.setName(testObject.getName());
        controlObject.setNickname(testObject.getNickname());
        controlObject.setSkillLevel(testObject.getSkillLevel());
        controlObject.setTrainerId(testObject.getTrainer().getId());
        controlObject.setType(testObject.getType());

        controlObject2.setId(testObject2.getId());
        controlObject2.setName(testObject2.getName());
        controlObject2.setNickname(testObject2.getNickname());
        controlObject2.setSkillLevel(testObject2.getSkillLevel());
        controlObject2.setTrainerId(testObject2.getTrainer().getId());
        controlObject2.setType(testObject2.getType());

        testCollection1.add(testObject);
        testCollection1.add(testObject2);

        testCollection2.add(testObject2);
        testCollection2.add(testObject);

        controlList.add(controlObject2);
        controlList.add(controlObject);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        trainerService.deleteTrainer(trainer);
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void testInjection() {
        assertNotNull(mappingService, "Service has not been injeted. Check annotations.");
    }

    /**
     * Test of map method, of class MappingServiceImpl.
     */
    @Test
    public void testMap_Collection_Class() {
        List<PokemonDTO> result = mappingService.map(testCollection1, PokemonDTO.class);

        assertTrue(result.size() == 2, "Mapped set does not contains all items.");
        assertTrue(result.containsAll(controlList), "Mapped set does not conaint expected items.");

        result = mappingService.map(testCollection2, PokemonDTO.class);
        assertTrue(result.size() == 2, "Mapped list does not contains all items.");
        assertTrue(result.containsAll(controlList), "Mapped list does not conaint expected items.");
        assertEquals(result, controlList, "Mapped list does not contain objects in the same order.");
    }

    /**
     * Test of map method, of class MappingServiceImpl.
     */
    @Test
    public void testMap_Object_Class() {
        PokemonDTO result = mappingService.map(testObject, PokemonDTO.class);

        assertEquals(result, controlObject, "Object has not been mapped correctly.");

        Pokemon result2 = mappingService.map(controlObject2, Pokemon.class);
        assertEquals(result2, testObject2, "Object has not been mapped correctly");
    }

    /**
     * Test of getMapper method, of class MappingServiceImpl.
     */
    @Test
    public void testGetMapper() {
        assertNotNull(mappingService.getMapper(), "Returned mapper is null.");
        assertSame(mappingService.getMapper(), mapper, "Returned mapper is not the same as the one in the spring context.");
    }

}
