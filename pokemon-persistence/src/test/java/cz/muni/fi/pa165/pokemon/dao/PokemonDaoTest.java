package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;

import java.sql.Date;

import junit.framework.Assert;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test if implementation of PokemonDao is correct.
 *
 * @author Milos Bartak
 */
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class PokemonDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private EntityManager entityManager;

    @Inject
    private PokemonDao pokemonDao;

    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Trainer trainer;
    
    @BeforeMethod
    public void setUpMethod() throws Exception {
        entityManager = emf.createEntityManager();
        trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(Date.valueOf("1988-12-31"));
        trainer.setStadium(null);

        pokemon1 = new Pokemon();
        pokemon1.setName("Squirtle");
        pokemon1.setNickname("Master blaster");
        pokemon1.setType(PokemonType.WATER);
        pokemon1.setSkillLevel(7);
        pokemon1.setTrainer(trainer);

        trainer.addPokemon(pokemon1);

        pokemon2 = new Pokemon();
        pokemon2.setName("Onix");
        pokemon2.setNickname("nyxnyx");
        pokemon2.setType(PokemonType.ROCK);
        pokemon2.setTrainer(trainer);
        
        entityManager.getTransaction().begin();
        entityManager.persist(trainer);
        entityManager.persist(pokemon1);
        entityManager.persist(pokemon2);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Pokemon pokemon = entityManager.find(Pokemon.class, pokemon1.getId());
        if (pokemon != null) {
            entityManager.remove(pokemon);
        }
        pokemon = entityManager.find(Pokemon.class, pokemon2.getId());
        if (pokemon != null) {
            entityManager.remove(pokemon);
        }
        Trainer foundTrainer = entityManager.find(Trainer.class, trainer.getId());
        if(foundTrainer != null) {
            for(Pokemon p : foundTrainer.getPokemons()) {
                entityManager.remove(p);
            }
            entityManager.remove(foundTrainer);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createNullTest() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(null);
        pokemon.setNickname("nick");
        pokemon.setType(PokemonType.ICE);
        pokemonDao.create(pokemon);
    }

    @Test
    public void testCreate() {
        entityManager = emf.createEntityManager();


        Pokemon pokemon3 = new Pokemon();
        pokemon3.setName("Gengar");
        pokemon3.setNickname("gangsta");
        pokemon3.setSkillLevel(7);
        pokemon3.setType(PokemonType.GHOST);
        pokemon3.setTrainer(trainer);
        pokemonDao.create(pokemon3);
        
        Pokemon found = entityManager.find(Pokemon.class, pokemon3.getId());
        
        Assert.assertNotNull("Pokemon " +
                pokemon3.getName() +
                "with id: " +
                pokemon3.getId() +
                "not found"
                , found);


        Assert.assertEquals("Persisted pokemon with id: " +
                pokemon3.getId() +
                "does not match found pokemon with id: " +
                found.getId()
                , found, pokemon3);


        try {
            pokemonDao.create(pokemon1);
            Assert.fail("Pokemon was persisted twice");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void updateTest() {
        entityManager = emf.createEntityManager();

        int level = pokemon1.getSkillLevel();
        pokemon1.setSkillLevel(level + 1);

        pokemonDao.update(pokemon1);
        Pokemon pokemon = entityManager.find(Pokemon.class, pokemon1.getId());
        Assert.assertEquals("Level did not get updated", level + 1, pokemon.getSkillLevel());
    }

    @Test
    public void deleteTest() {
        entityManager = emf.createEntityManager();
        pokemonDao.delete(pokemon1);

        Pokemon toBeDeleted = entityManager.find(Pokemon.class, pokemon1.getId());
        Assert.assertNull("Pokemon" +
                        pokemon1.getName() +
                        "with id: " +
                        pokemon1.getId() +
                        "was not deleted.",
                toBeDeleted);
    }

    @Test
    public void findByIdTest() {
        Pokemon found = pokemonDao.findById(pokemon2.getId());

        Assert.assertEquals("Pokemon found by id:" +
                found.getId() +
                "does not match expected pokemon with id" +
                pokemon2.getId()
                , pokemon2, found);
    }
}
