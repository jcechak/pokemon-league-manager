package cz.muni.fi.pa165.pokemon.dao;

import javax.inject.Inject;

import cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.List;

/**
 * This class tests StadiumDao implementation
 *
 * @author Dominika Talianova
 */
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class StadiumDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Stadium stadium1;
    private Stadium stadium2;

    private Trainer trainer1;

    @Inject
    private StadiumDao stadiumDao;

    public StadiumDaoTest() {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {

        trainer1 = new Trainer();
        trainer1.setName("Ash");
        trainer1.setSurname("Ketchum");
        trainer1.setDateOfBirth(Date.valueOf("1993-10-14"));

        stadium1 = new Stadium();
        stadium1.setCity("Trovita");
        stadium1.setType(PokemonType.POISON);

        stadium2 = new Stadium();
        stadium2.setCity("Mikan");
        stadium2.setType(PokemonType.FIRE);

        stadium2.setLeader(trainer1);
        trainer1.setStadium(stadium2);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(trainer1);
        em.persist(stadium1);
        em.persist(stadium2);
        em.getTransaction().commit();

    }


    @AfterMethod
    public void tearDown() throws Exception {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Stadium persisted = em.find(Stadium.class, stadium1.getId());
        if (persisted != null) {
            em.remove(persisted);
        }

        persisted = em.find(Stadium.class, stadium2.getId());
        if (persisted != null) {
            em.remove(persisted);
        }

        Trainer persistedTrainer = em.find(Trainer.class, trainer1.getId());
        if(persistedTrainer != null){
            em.remove(persistedTrainer);
        }

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void testCreate() throws Exception {
        EntityManager em = emf.createEntityManager();

        Assert.assertNotNull("Persisting stadium does not set id.", stadium1.getId());
        Assert.assertNotNull("Persisting stadium does not set id.", stadium2.getId());

        Stadium persistedStadium1 = em.find(Stadium.class, stadium1.getId());
        Stadium persistedStadium2 = em.find(Stadium.class, stadium2.getId());
        Assert.assertNotNull("Persisted stadium not found.", persistedStadium1);
        Assert.assertEquals("Persisted stadium does not equal to the original.", persistedStadium1, stadium1);
        Assert.assertNotNull("Persisted stadium not found.", persistedStadium2);
        Assert.assertEquals("Persisted stadium does not equal to the original.", persistedStadium2, stadium2);

        try {
            stadiumDao.create(stadium2);
            Assert.fail("Created the same stadium twice.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testUpdate() throws Exception {
        EntityManager em = emf.createEntityManager();

        stadium1.setCity("Navel");
        stadiumDao.update(stadium1);
        Stadium persistedStadium1 = em.find(Stadium.class, stadium1.getId());
        Assert.assertNotNull("Nothing was changed.", persistedStadium1.getCity());
        Assert.assertEquals("Changed city does not equal with original one.", persistedStadium1.getCity(), "Navel");

    }

    @Test
    public void testDelete() throws Exception {
        EntityManager em = emf.createEntityManager();

        stadiumDao.delete(stadium1);
        Stadium persistedStadium1 = em.find(Stadium.class, stadium1.getId());
        Assert.assertNull("Delete called, but item found in DB.", persistedStadium1);

    }

    @Test
    public void testFindById() throws Exception {
        Stadium foundStadium2 = stadiumDao.findById(stadium2.getId());
        Assert.assertEquals("Found stadium does not equal to the original one.", foundStadium2, stadium2);
    }

    @Test
    public void testFindByCity() throws Exception {
        Stadium foundStadium2 = stadiumDao.findByCity(stadium2.getCity());
        Assert.assertEquals("Found stadium does not equal to the original one.", foundStadium2, stadium2);
    }

    @Test
    public void testFindByStadiumLeader() throws Exception {
        Stadium foundStadium2 = stadiumDao.findByStadiumLeader(stadium2.getLeader());
        Assert.assertEquals("Found stadium does not equal to the original one.", foundStadium2, stadium2);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Stadium> foundStadiums = stadiumDao.findAll();
        if (foundStadiums.size() != 2) {
            Assert.fail("Returned list has " + foundStadiums.size() + " items.");
        } else if (!foundStadiums.contains(stadium1) || !foundStadiums.contains(stadium2)) {
            Assert.fail("The list does not contain expected items.");
        }
    }

    @Test
    public void testFindByPokemonType() throws Exception {
        List<Stadium> foundStadiums = stadiumDao.findByPokemonType(PokemonType.FIRE);
        if (!foundStadiums.contains(stadium2)) {
            Assert.fail("The list does not contain expected items.");
        } else if (foundStadiums.contains(stadium1)) {
            Assert.fail("The list contains an item that should not be there.");
        }
    }

}
