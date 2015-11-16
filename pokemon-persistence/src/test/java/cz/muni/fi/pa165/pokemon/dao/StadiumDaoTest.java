package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.sql.Date;
import java.util.List;

import static org.testng.Assert.*;


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

        assertNotNull(stadium1.getId(), "Persisting stadium does not set id.");
        assertNotNull(stadium2.getId(), "Persisting stadium does not set id.");

        Stadium persistedStadium1 = em.find(Stadium.class, stadium1.getId());
        Stadium persistedStadium2 = em.find(Stadium.class, stadium2.getId());
        assertNotNull(persistedStadium1, "Persisted stadium not found.");
        assertEquals(persistedStadium1, stadium1, "Persisted stadium does not equal to the original.");
        assertNotNull(persistedStadium2, "Persisted stadium not found.");
        assertEquals(persistedStadium2, stadium2, "Persisted stadium does not equal to the original.");

        try {
            stadiumDao.create(stadium2);
            fail("Created the same stadium twice.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testUpdate() throws Exception {
        EntityManager em = emf.createEntityManager();

        stadium1.setCity("Navel");
        stadiumDao.update(stadium1);
        Stadium persistedStadium1 = em.find(Stadium.class, stadium1.getId());
        assertNotNull(persistedStadium1.getCity(), "Nothing was changed.");
        assertEquals(persistedStadium1.getCity(), stadium1.getCity() , "Changed city does not equal with original one.");

    }

    @Test
    public void testDelete() throws Exception {
        EntityManager em = emf.createEntityManager();

        stadiumDao.delete(stadium1);
        Stadium persistedStadium1 = em.find(Stadium.class, stadium1.getId());
        assertNull(persistedStadium1, "Delete called, but item found in DB.");

    }

    @Test
    public void testFindById() throws Exception {
        Stadium foundStadium2 = stadiumDao.findById(stadium2.getId());
        assertEquals(foundStadium2, stadium2, "Found stadium does not equal to the original one.");
    }

    @Test
    public void testFindByCity() throws Exception {
        Stadium foundStadium2 = stadiumDao.findByCity(stadium2.getCity());
        assertEquals(foundStadium2, stadium2, "Found stadium does not equal to the original one.");
    }

    @Test
    public void testFindByStadiumLeader() throws Exception {
        Stadium foundStadium2 = stadiumDao.findByStadiumLeader(stadium2.getLeader());
        assertEquals(foundStadium2, stadium2, "Found stadium does not equal to the original one.");
    }

    @Test
    public void testFindAll() throws Exception {
        List<Stadium> foundStadiums = stadiumDao.findAll();
        if (foundStadiums.size() != 2) {
            fail("Returned list has " + foundStadiums.size() + " items.");
        } else if (!foundStadiums.contains(stadium1) || !foundStadiums.contains(stadium2)) {
            fail("The list does not contain expected items.");
        }
    }

    @Test
    public void testFindByPokemonType() throws Exception {
        List<Stadium> foundStadiums = stadiumDao.findByPokemonType(PokemonType.FIRE);
        if (!foundStadiums.contains(stadium2)) {
            fail("The list does not contain expected items.");
        } else if (foundStadiums.contains(stadium1)) {
            fail("The list contains an item that should not be there.");
        }
    }

}
