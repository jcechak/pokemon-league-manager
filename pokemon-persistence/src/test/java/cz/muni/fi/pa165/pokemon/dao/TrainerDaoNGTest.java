package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Trainer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.*;

import javax.inject.Inject;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import org.springframework.dao.DataAccessException;

import static org.testng.Assert.*;

/**
 * Tests for TrainerDaoImpl class.
 *
 * @author Jaroslav Cechak
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration.class})
@Transactional
public class TrainerDaoNGTest extends AbstractTransactionalTestNGSpringContextTests {

    @Inject
    private TrainerDao trainerDao;

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
     * needed to make beforeClass working. It does basically the same thing as
     * {@link javax.persistence.PersistenceUnit @PersistenceUnit} annotation.
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
        t2.setDateOfBirth(Date.valueOf("1990-10-11"));
    }

    /**
     * Prepares fixtures for tests
     * @throws Exception 
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        setupTrainer1();
        setupTrainer2();

        EntityManager e = emf.createEntityManager();
        e.getTransaction().begin();
        e.persist(t2);
        e.getTransaction().commit();
        e.close();
    }

    /**
     * Cleans up database
     * @throws Exception 
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
        EntityManager e = emf.createEntityManager();
        e.getTransaction().begin();
        e.remove(e.find(Trainer.class, t2.getId()));
        e.getTransaction().commit();
        e.close();
    }

    @BeforeTransaction
    public void beforeTransaction() {
    }

    /**
     * Assert there has not been any change to fixtures in the database and all
     * operations have been rolled back
     */
    @AfterTransaction
    public void afterTransaction() {
        setupTrainer1();
        setupTrainer2();

        List<Trainer> trainers = em.createQuery("SELECT t FROM Trainer t", Trainer.class)
                .getResultList();
        assertEquals(trainers.size(), 1, "Some test has not rolled back.");

        assertEquals(trainers.get(0), t2, "There has been a change to fixtures.");
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createNullTest() {
        Trainer trainer = new Trainer();
        trainer.setName("Brock");
        trainer.setSurname("Harrison");
        trainer.setStadium(null);
        trainerDao.create(trainer);
    }


    @Test(expectedExceptions = DataAccessException.class)
    public void createTwiceTest() {
        trainerDao.create(t2);
        trainerDao.create(t2);
    }


    /**
     * Test of create method, of class TrainerDaoImpl.
     */
    @Test
    public void testCreate() {
        trainerDao.create(t1);
        assertNotNull(t1.getId(), "Trainer did not received id when persisted (probably not persisted).");

        Trainer received = em.find(Trainer.class, t1.getId());

        assertEquals(received, t1, "Persisted object should equal to the one in persistence context.");
    }

    /**
     * Test of update method, of class TrainerDaoImpl.
     */
    @Test
    public void testUpdate() {
        t2.setName("Garr");
        t2.setSurname("Oa");
        t2.setDateOfBirth(now);

        trainerDao.update(t2);
        Trainer received = em.find(Trainer.class, t2.getId());

        assertEquals(received, t2, "Object in persistence context did not change.");
    }

    /**
     * Test of delete method, of class TrainerDaoImpl.
     */
    @Test
    public void testDelete() {
        trainerDao.delete(t2);
        Trainer received = em.find(Trainer.class, t2.getId());

        assertNull(received, "Trainer has not been deleted from persistence context.");
    }

    /**
     * Test of findById method, of class TrainerDaoImpl.
     */
    @Test
    public void testFindById() {
        Trainer received = trainerDao.findById(t2.getId());

        assertEquals(t2, received, "Received object is not as expected.");

    }

    /**
     * Test of findAll method, of class TrainerDaoImpl.
     */
    @Test
    public void testFindAll() {
        List<Trainer> trainers = trainerDao.findAll();

        assertTrue(trainers.size() == 1, "There should be exactly one trainer in db.");
        assertTrue(trainers.contains(t2), "Did not found all trainers.");

        em.remove(em.find(Trainer.class, t2.getId()));

        trainers = trainerDao.findAll();
        assertTrue(trainers.isEmpty(), "There should not be any trainer in database.");

    }

}
