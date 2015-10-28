package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Trainer;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for TrainerDaoImpl class.
 *
 * @author Jaroslav Cechak
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration.class})
@Transactional
public class TrainerDaoNGTest extends AbstractTransactionalTestNGSpringContextTests {

    // uncomment when TrainerDao and TrainerDaoImpl is implemented
    //@Inject
    //private static TrainerDao trainerDao;
    private static EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager em;

    private static final Trainer t1 = new Trainer();
    private static final Trainer t2 = new Trainer();

    private static Date now;

    public TrainerDaoNGTest() {
    }

    /**
     * Quite an ugly hack to make Spring inject static fields. It IS definitely
     * needed to make beforeClass working. It does basicaly the same thing as
     * @link{javax.persistence.PersistenceUnit @PersistenceUnit} annotation.
     *
     * @param emf injected entity manager factory
     */
    @PersistenceUnit
    public void setEntityManager(EntityManagerFactory emf) {
        TrainerDaoNGTest.emf = emf;

    }

    private static void setupTrainer1() {
        now = new Date(System.currentTimeMillis());
        
        t1.setName("Ash");
        t1.setSurname("Ketchum");
        t1.setDateOfBirth(now);
    }

    private static void setupTrainer2() {
        
        t2.setName("Garry");
        t2.setSurname("Oak");
        t2.setDateOfBirth(new Date(12345l));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        //trainerDao = new TrainerDaoImpl();
 
        setupTrainer1();
        setupTrainer2();

        // load trainer t2 as a fixture for tests
        EntityManager e = emf.createEntityManager();
        e.getTransaction().begin();
        e.persist(t2);
        e.getTransaction().commit();
        e.close();
    }

    /**
     * Assert there has not been any change to fixtures in database and all
     * operations have been rolledback
     */
    @AfterTransaction
    public void afterTransaction() {
        setupTrainer1();
        setupTrainer2();
        
        List<Trainer> trainers = em.createQuery("SELECT t FROM Trainer t", Trainer.class)
                .getResultList();

        assertEquals(trainers.size(), 1, "Some test did not rolledback.");
        assertEquals(trainers.get(0), t2, "There has been a change to fixtures.");
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
    @Test
    public void testCreate() {
        System.out.println("Testing create...");

        //trainerDao.create(t1);
        assertNotNull(t1.getId(), "Trainer did not recieved id when persisted (probably not persisted).");

        Trainer received = em.find(Trainer.class, t1.getId());

        assertEquals(received, t1, "Persisted object should equal to the one in persitance context.");
    }

    /**
     * Test of update method, of class TrainerDaoImpl.
     */
    @Test
    public void testUpdate() {
        System.out.println("Testing update...");

        t2.setName("Garr");
        t2.setSurname("Oa");
        t2.setDateOfBirth(now);

        //trainerDao.update(t2);
        Trainer received = em.find(Trainer.class, t2.getId());

        assertEquals(received, t2, "Object in peristance context did not change.");
    }

    /**
     * Test of delete method, of class TrainerDaoImpl.
     */
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
