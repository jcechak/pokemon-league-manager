package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.stereotype.Repository;

/**
 * Implementation of Stadium data acces object having basic CRUD operations.
 * 
 * @author Milos Bartak
 */
@Repository
@Transactional
public class StadiumDaoImpl implements StadiumDao {

    @PersistenceContext
    private EntityManager entityManager;
     
    @Override
    public Stadium findById(Long id) {
        try{
            return entityManager.find(Stadium.class, id);
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to find object, because received object is not an entity or primary key is not valid.", e);
        }
    }

    @Override
    public void create(Stadium stadium) {
        try {
            entityManager.persist(stadium);
        } catch(EntityExistsException e) {
            throw new DataIntegrityViolationException("Unable to persist stadium " + stadium.toString() + ", because this stadium is already persisted", e);
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to create object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to create stadium due to database access failure.", e);
        }
    }
    
    @Override
    public void update(Stadium stadium) {
        try {
            entityManager.merge(stadium);
            entityManager.flush();
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to update object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to update stadium due to database access failure.", e);
        }
    }

    @Override
    public void delete(Stadium stadium) {
        try{
            entityManager.remove(entityManager.find(Stadium.class, stadium.getId()));
            entityManager.flush();
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to remove object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to remove stadium due to database access failure.", e);
        }
    }

    @Override
    public List<Stadium> findAll() {
        try{
            return entityManager.createQuery("SELECT s FROM Stadium s", Stadium.class)
                .getResultList();
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to find all objects.", e);
        } catch(PersistenceException e) {
            throw new DataRetrievalFailureException("Unable to retrieve data with selected query", e);
        }
    }

    @Override
    public Stadium findByCity(String city) {
        try {
            return entityManager.createQuery("SELECT s FROM Stadium s WHERE s.city = :c", Stadium.class)
                .setParameter("c", city)
                .getSingleResult();
        } catch(NoResultException noResult) {
            return null;
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to remove object, because received object is not an entity.", e);
        } catch(PersistenceException e) {
            throw new DataRetrievalFailureException("Unable to retrieve data with selected query", e);
        }
    }

    @Override
    public Stadium findByStadiumLeader(Trainer leader) {
        try {
            return entityManager.createQuery("SELECT s FROM Stadium s WHERE s.leader = :l", Stadium.class)
                .setParameter("l", leader)
                .getSingleResult();
        } catch(NoResultException noResult) {
            return null;
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to remove object, because received object is not an entity.", e);
        } catch(PersistenceException e) {
            throw new DataRetrievalFailureException("Unable to retrieve data with selected query", e);
        }
    }

    @Override
    public List<Stadium> findByPokemonType(PokemonType type) {
        try {
            return entityManager.createQuery("SELECT s FROM Stadium s WHERE s.type = :t", Stadium.class)
                    .setParameter("t", type)
                    .getResultList();
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to remove object, because received object is not an entity.", e);
        } catch(PersistenceException e) {
            throw new DataRetrievalFailureException("Unable to retrieve data with selected query", e);
        }
    }
    
}
