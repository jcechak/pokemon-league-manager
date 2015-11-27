package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Tournament;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.stereotype.Repository;

/**
 * Implementation of Tournament Dao
 * 
 * @author Milos Bartak
 */
@Repository
@Transactional
public class TournamentDaoImpl implements TournamentDao{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void create(Tournament tournament) {
        try {
            entityManager.persist(tournament);
            entityManager.flush();
        } catch(EntityExistsException e) {
            throw new DataIntegrityViolationException("Unable to persist tournament " + tournament.toString() + ", because this tournament is already persisted", e);
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to create object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to create tournament due to database access failure.", e);
        }
    }

    @Override
    public void update(Tournament tournament) {
        try {
            entityManager.merge(tournament);
            entityManager.flush();
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to update object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to update tournament due to database access failure.", e);
        }
    }

    @Override
    public void delete(Tournament tournament) {
        try{
            entityManager.remove(entityManager.find(Tournament.class, tournament.getId()));
            entityManager.flush();
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to remove object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to remove tournament due to database access failure.", e);
        }
    }

    @Override
    public Tournament findById(Long id) {
        try{
            return entityManager.find(Tournament.class, id);
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to find object, because received object is not an entity or primary key is not valid.", e);
        }
    }
    
}
