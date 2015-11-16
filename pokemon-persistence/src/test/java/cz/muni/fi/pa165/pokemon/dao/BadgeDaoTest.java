package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration;
import cz.muni.fi.pa165.pokemon.entity.Badge;
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
 * This class tests BadgeDao implementation.
 *
 * @author Marek Sabo
 */
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class BadgeDaoTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Badge badge1;
    private Badge badge2;

    private Stadium stadium;

    private Trainer trainer;

    @Inject
    private BadgeDao badgeDao;

    public BadgeDaoTest() {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {

        stadium = new Stadium();
        stadium.setType(PokemonType.DRAGON);
        stadium.setCity("Brno");

        trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setSurname("Hash");
        trainer.setStadium(stadium);
        trainer.setDateOfBirth(Date.valueOf("1965-01-28"));
        stadium.setLeader(trainer);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(stadium);
        em.persist(trainer);
        em.getTransaction().commit();

        badge1 = new Badge();
        badgeDao.create(badge1);

        badge2 = new Badge();
        badge2.setStadium(stadium);
        badge2.setTrainer(trainer);
        badgeDao.create(badge2);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Badge persisted = em.find(Badge.class, badge1.getId());
        if (persisted != null) {
            em.remove(persisted);
        }
        persisted = em.find(Badge.class, badge2.getId());
        if (persisted != null) {
            em.remove(persisted);
        }
        Trainer persistedTrainer = em.find(Trainer.class, trainer.getId());
        if (persistedTrainer != null) {
            em.remove(persistedTrainer);
        }
        Stadium persistedStadium = em.find(Stadium.class, stadium.getId());
        if (persistedStadium != null) {
            em.remove(persistedStadium);
        }

        List<Trainer> trainers = em.createQuery("SELECT t FROM Trainer t", Trainer.class)
                .getResultList();
        List<Stadium> stadiums = em.createQuery("SELECT s FROM Stadium s", Stadium.class)
                .getResultList();
        List<Badge> badges = em.createQuery("SELECT b FROM Badge b", Badge.class)
                .getResultList();
        assertEquals(trainers.size(), 0, "Some test did not rollback.");
        assertEquals(stadiums.size(), 0, "Some test did not rollback.");
        assertEquals(badges.size(), 0, "Some test did not rollback.");
        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void testCreate() throws Exception {
        EntityManager em = emf.createEntityManager();

        assertNotNull(badge1.getId(), "Persisting badge does not set id.");
        assertNotNull(badge2.getId(), "Persisting badge does not set id.");

        Badge persistedBadge1 = em.find(Badge.class, badge1.getId());
        Badge persistedBadge2 = em.find(Badge.class, badge2.getId());
        assertNotNull(persistedBadge1, "Persisted badge not found.");
        assertEquals(persistedBadge1, badge1, "Persisted badge does not equal to the original one.");

        assertNotNull(persistedBadge2, "Persisted badge not found.");
        assertEquals(persistedBadge2, badge2, "Persisted badge does not equal to the original one.");

        try {
            badgeDao.create(badge2);
            fail("Created the same badge twice.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testUpdate() throws Exception {
        EntityManager em = emf.createEntityManager();

        badge1.setStadium(stadium);
        badgeDao.update(badge1);
        Badge persistedBadge1 = em.find(Badge.class, badge1.getId());
        assertNotNull(persistedBadge1.getStadium(), "Update did not changed anything.");
        assertEquals(persistedBadge1.getStadium(), stadium, "Changed Stadium does not equal with original one.");
    }

    @Test
    public void testDelete() throws Exception {

        EntityManager em = emf.createEntityManager();

        badgeDao.delete(badge1);
        Badge persistedBadge1 = em.find(Badge.class, badge1.getId());
        assertNull(persistedBadge1, "Delete called but item found in DB.");
    }

    @Test
    public void testFindById() throws Exception {
        Badge foundBadge = badgeDao.findById(badge2.getId());
        assertEquals(foundBadge, badge2, "Persisted badge does not equal to the original one.");
    }

    @Test
    public void testFindAll() throws Exception {
        List<Badge> badges = badgeDao.findAll();
        if (badges.size() != 2) {
            fail("Returned list has " + badges.size() + " items.");
        } else if (!badges.contains(badge1) || !badges.contains(badge2)) {
            fail("List does not contain expected items.");
        }
    }

    @Test
    public void testFindAllWithTrainer() throws Exception {
        List<Badge> badges = badgeDao.findAllWithTrainer(badge2.getTrainer());
        assertTrue(badges.size() == 1, "Returned list has " + badges.size() + " items.");
        assertTrue(badges.contains(badge2), "Returned list does not contain right badge.");
    }

    @Test
    public void testFindAllWithStadium() throws Exception {
        List<Badge> badges = badgeDao.findAllWithStadium(badge2.getStadium());
        assertTrue(badges.size() == 1, "Returned list has " + badges.size() + " items.");
        assertTrue(badges.contains(badge2), "Returned list does not contain right badge.");
    }

    @Test
    public void testFindAllTrainerAndStadium() throws Exception {
        Badge badge = badgeDao.findByTrainerAndStadium(badge2.getTrainer(), badge2.getStadium());
        assertEquals(badge.getTrainer(), badge2.getTrainer(), "Trainers does not equal.");
        assertEquals(badge.getStadium(), badge2.getStadium(), "Stadiums does not equal.");
        assertEquals(badge, badge2, "Badges does not equal.");
    }
}
