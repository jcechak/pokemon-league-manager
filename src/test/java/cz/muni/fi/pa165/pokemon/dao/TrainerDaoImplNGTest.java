package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.context.PersistanceConfiguration;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for TrainerDaoImpl class
 * @author Jaroslav Cechak
 */
@ContextConfiguration(classes = PersistanceConfiguration.class)
@Transactional
public class TrainerDaoImplNGTest {

    // uncomment when TrainerDao and TrainerDaoImpl is implemented
    //private static TrainerDao trainerDao;

    @PersistenceContext
    private static EntityManager em;

    private static Trainer t1;
    private static Trainer t2;

    private static Date now;

    public TrainerDaoImplNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        //trainerDao = new TrainerDaoImpl();
        
        now = new Date(System.currentTimeMillis());

        t1 = new Trainer();
        t1.setName("Ash");
        t1.setSurname("Ketchum");
        t1.setDateOfBirth(now);

        t2 = new Trainer();
        t2.setName("Garry");
        t2.setSurname("Oak");
        t2.setDateOfBirth(new Date(12345l));
        
        em.persist(t2);
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
    
    

    /**
     * Test of create method, of class TrainerDaoImpl.
     */
    @Rollback(true)
    @Test
    public void testCreate() {
        System.out.println("Testing create...");

        //trainerDao.create(t1);

        assertNotNull(t1.getId(), "Trainer did not recieved id when persisted.");

        Trainer received = em.find(Trainer.class, t1.getId());

        assertEquals(t1, received, "Persisted object should equal to the one in persitance context.");
    }

    /**
     * Test of update method, of class TrainerDaoImpl.
     */
    @Rollback(true)
    @Test
    public void testUpdate() {
        System.out.println("Testing update...");
        
        t2.setName("Garr");
        t2.setSurname("Oa");
        t2.setDateOfBirth(now);
        
        //trainerDao.update(t2);
        
        Trainer received = em.find(Trainer.class, t2.getId());
        
        assertEquals(t2, received, "Object in peristance context did not change.");
        
    }

    /**
     * Test of delete method, of class TrainerDaoImpl.
     */
    @Rollback(true)
    @Test
    public void testDelete() {
        System.out.println("Testing delete...");
        
        //trainerDao.delete(t2);
        
        Trainer received = em.find(Trainer.class, t2.getId());
        
        assertNull(received, "Trainer has not been deleted from perisistance context.");
    }

    /**
     * Test of findById method, of class TrainerDaoImpl.
     */
    @Rollback(true)
    @Test
    public void testFindById() {
        System.out.println("Testing findById...");
        
        /*Trainer received = trainerDao.findById(t2.getId());
        
        assertEquals(t2, received, "Received object is not as expected.");
        */
    }

    /**
     * Test of findAll method, of class TrainerDaoImpl.
     */
    @Rollback(true)
    @Test
    public void testFindAll() {
        System.out.println("Testing findAll...");
        
        /*List<Trainer> trainers = trainerDao.findAll();
        
        assertTrue(trainers.size() == 1, "There should be exctly one trainer in db.");
        assertTrue(trainers.contains(t2), "Did not found all trainers.");
        
        em.remove(em.find(Trainer.class, t2.getId()));
        
        trainers = trainerDao.findAll();
        assertTrue(trainers.isEmpty(), "There should not be any trainer in database.");
                */
    }
    
}