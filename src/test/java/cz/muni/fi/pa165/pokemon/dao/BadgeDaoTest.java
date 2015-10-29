package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import junit.framework.Assert;
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

    @Inject
    private BadgeDao badgeDao;

    public BadgeDaoTest() {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {

        stadium = new Stadium();
        stadium.setType(PokemonType.DRAGON);
        stadium.setCity("Brno");

        Trainer trainer = new Trainer();
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
        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void testCreate() throws Exception {
        EntityManager em = emf.createEntityManager();

        Assert.assertNotNull("Persisting badge does not set id.", badge1.getId());
        Assert.assertNotNull("Persisting badge does not set id.", badge2.getId());

        Badge persistedBadge1 = em.find(Badge.class, badge1.getId());
        Badge persistedBadge2 = em.find(Badge.class, badge2.getId());
        Assert.assertNotNull("Persisted badge not found.", persistedBadge1);
        Assert.assertEquals("Persisted badge does not equal to the original one.", persistedBadge1, badge1);

        Assert.assertNotNull("Persisted badge not found.", persistedBadge2);
        Assert.assertEquals("Persisted badge does not equal to the original one.", persistedBadge2, badge2);

        try {
            badgeDao.create(badge2);
            Assert.fail("Created the same badge twice.");
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testUpdate() throws Exception {
        EntityManager em = emf.createEntityManager();

        badge1.setStadium(stadium);
        badgeDao.update(badge1);
        Badge persistedBadge1 = em.find(Badge.class, badge1.getId());
        Assert.assertNotNull("Update did not changed anything.", persistedBadge1.getStadium());
        Assert.assertEquals("Changed Stadium does not equal with original one.", persistedBadge1.getStadium(), stadium);
    }

    @Test
    public void testDelete() throws Exception {

        EntityManager em = emf.createEntityManager();

        badgeDao.delete(badge1);
        Badge persistedBadge1 = em.find(Badge.class, badge1.getId());
        Assert.assertNull("Delete called but item found in DB.", persistedBadge1);
    }

    @Test
    public void testFindById() throws Exception {
        Badge foundBadge = badgeDao.findById(badge2.getId());
        Assert.assertEquals("Persisted badge does not equal to the original one.", foundBadge, badge2);
    }

    @Test
    public void testFindAll() throws Exception {
        List<Badge> badges = badgeDao.findAll();
        if (badges.size() != 2) {
            Assert.fail("Returned list has " + badges.size() + " items.");
        } else if (!badges.contains(badge1) || !badges.contains(badge2)) {
            Assert.fail("List does not contain expected items.");
        }
    }

    @Test
    public void testFindAllWithTrainer() throws Exception {
        List<Badge> badges = badgeDao.findAllWithTrainer(badge2.getTrainer());
        Assert.assertTrue("Returned list has " + badges.size() + " items.", badges.size() == 1);
        Assert.assertTrue("Returned list does not contain right badge.", badges.contains(badge2));
    }

    @Test
    public void testFindAllWithStadium() throws Exception {
        List<Badge> badges = badgeDao.findAllWithStadium(badge2.getStadium());
        Assert.assertTrue("Returned list has " + badges.size() + " items.", badges.size() == 1);
        Assert.assertTrue("Returned list does not contain right badge.", badges.contains(badge2));
    }

    @Test
    public void testFindAllTrainerAndStadium() throws Exception {
        Badge badge = badgeDao.findByTrainerAndStadium(badge2.getTrainer(), badge2.getStadium());
        Assert.assertEquals("Trainers does not equal.", badge.getTrainer(), badge2.getTrainer());
        Assert.assertEquals("Stadiums does not equal.", badge.getStadium(), badge2.getStadium());
        Assert.assertEquals("Badges does not equal.", badge, badge2);
    }
}
