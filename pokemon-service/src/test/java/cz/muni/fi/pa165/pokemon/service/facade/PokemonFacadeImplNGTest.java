package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
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
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class PokemonFacadeImplNGTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private Mapper mapper;
        
    public PokemonFacadeImplNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void test() {
        assertNotNull(mapper, "Mapper is null.");
        
        Trainer t = new Trainer();
        t.setId(11l);
        t.setName("Ash");
        
        Pokemon p = new Pokemon();
        p.setId(2l);
        p.setName("Pika");
        p.setNickname("Chu");
        p.setSkillLevel(20);
        p.setType(PokemonType.ELECTRIC);
        p.setTrainer(t);
                
        PokemonDTO pd = mapper.map(p, PokemonDTO.class);
        
        System.out.println(p);
        
        System.out.println(pd);
        
        Pokemon p2 = mapper.map(pd, Pokemon.class);
        
        System.out.println(p2);
    }
}
