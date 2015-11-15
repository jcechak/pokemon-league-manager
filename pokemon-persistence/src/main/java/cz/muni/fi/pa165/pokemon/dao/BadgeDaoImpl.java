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
        em.persist(badge);

    }

    @Override
    public void update(Badge badge) {
        em.merge(badge);
    }

    @Override
    public void delete(Badge badge) {
        em.remove(em.find(Badge.class, badge.getId()));
    }

    @Override
    public Badge findById(Long id) {
        return em.find(Badge.class, id);
    }

    @Override
    public List<Badge> findAll() {
        return em.createQuery("SELECT b FROM Badge b", Badge.class)
                .getResultList();

    }

    @Override
    public List<Badge> findAllWithTrainer(Trainer trainer) {
        return em.createQuery("SELECT b FROM Badge b "
                + "WHERE b.trainer = :t", Badge.class)
                .setParameter("t", trainer)
                .getResultList();
    }

    @Override
    public List<Badge> findAllWithStadium(Stadium stadium) {
        return em.createQuery("SELECT b FROM Badge b "
                + "WHERE b.stadium = :s", Badge.class)
                .setParameter("s", stadium)
                .getResultList();
    }

    @Override
    public Badge findByTrainerAndStadium(Trainer trainer, Stadium stadium) {
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
