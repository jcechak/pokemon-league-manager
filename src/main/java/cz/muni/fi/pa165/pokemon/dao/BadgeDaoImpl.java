package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 * Implements CRUD operations with Badge entity.
 *
 * @author Jaroslav Cechak
 */
@Repository
@Transactional
public class BadgeDaoImpl implements BadgeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Badge badge) {
        if (badge == null) {
            throw new NullPointerException("Badge is null.");
        }
        em.persist(badge);

    }

    @Override
    public void update(Badge badge) {
        if (badge == null) {
            throw new NullPointerException("Badge is null.");
        }
        em.merge(badge);
    }

    @Override
    public void delete(Badge badge) {
        if (badge == null) {
            throw new NullPointerException("Badge is null.");
        }
        em.remove(em.find(Badge.class, badge.getId()));
    }

    @Override
    public Badge findById(Long id) {
        if (id == null) {
            throw new NullPointerException("Id is null.");
        }
        return em.find(Badge.class, id);
    }

    @Override
    public List<Badge> findAll() {
        return em.createQuery("SELECT b FROM Badge b", Badge.class)
                .getResultList();

    }

    @Override
    public List<Badge> findAllWithTrainer(Trainer trainer) {
        if (trainer == null) {
            throw new NullPointerException("Trainer is null.");
        }
        return em.createQuery("SELECT b FROM Badge b "
                + "WHERE b.trainer = :t", Badge.class)
                .setParameter("t", trainer)
                .getResultList();
    }

    @Override
    public List<Badge> findAllWithStadium(Stadium stadium) {
        if (stadium == null) {
            throw new NullPointerException("Stadium is null.");
        }
        return em.createQuery("SELECT b FROM Badge b "
                + "WHERE b.stadium = :s", Badge.class)
                .setParameter("s", stadium)
                .getResultList();
    }

    @Override
    public Badge findByTrainerAndStadium(Trainer trainer, Stadium stadium) {
        if (trainer == null) {
            throw new NullPointerException("Trainer is null.");
        }

        if (stadium == null) {
            throw new NullPointerException("Stadium is null.");
        }
        try {
            return em.createQuery("SELECT b FROM Badge b "
                    + "WHERE b.trainer = :t "
                    + "AND b.stadium = :s", Badge.class)
                    .setParameter("t", trainer)
                    .setParameter("s", stadium)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
