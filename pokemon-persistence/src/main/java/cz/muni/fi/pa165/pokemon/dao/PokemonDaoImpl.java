package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;

/**
 * Class implementing PokemonDao interface.
 * @author Marek Sabo
 */
@Repository
@Transactional
public class PokemonDaoImpl implements PokemonDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Pokemon pokemon) {
        try {
            em.persist(pokemon);
            em.flush();
        } catch(EntityExistsException e) {
            throw new DataIntegrityViolationException("Unable to persist pokemon " + pokemon.toString() + ", because this pokemon is already persisted", e);
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to create object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to create stadium due to database access failure.", e);
        }
    }

    @Override
    public void update(Pokemon pokemon) {
        try {
            em.merge(pokemon);
            em.flush();
        } catch (TransactionRequiredException | IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to update pokemon due to database access failure.", e);
        } catch (ValidationException | PersistenceException e) {
            throw new DataIntegrityViolationException("Unable to update pokemon due to integrity violation.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to update object, because received object is not " +
                    "an entity or this entity has been already removed.", iae);
        }
    }

    @Override
    public void delete(Pokemon pokemon) {
        try {
            em.remove(findById(pokemon.getId()));
            em.flush();
        } catch (TransactionRequiredException | IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to update pokemon due to database access failure.", e);
        } catch (ValidationException | PersistenceException e) {
            throw new DataIntegrityViolationException("Unable to update pokemon due to integrity violation.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to update object, because received object is not " +
                    "an entity or this entity has been already removed.", iae);
        }
    }

    @Override
    public Pokemon findById(Long id) {
        try {
            return em.find(Pokemon.class, id);
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to update object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to update pokemon due to database access failure.", e);
        }

    }

    @Override
    public List<Pokemon> findAll() {
        try {
            return em.createQuery("SELECT p FROM Pokemon p", Pokemon.class).getResultList();
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to update object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to update pokemon due to database access failure.", e);
        }
    }

    @Override
    public List<Pokemon> findAllWithTrainer(Trainer trainer) {
        try {
            return em.createQuery("SELECT p FROM Pokemon p WHERE p.trainer = :t", Pokemon.class)
                    .setParameter("t", trainer)
                    .getResultList();
        } catch(IllegalArgumentException e) {
            throw new InvalidDataAccessApiUsageException("Unable to update object, because received object is not an entity.", e);
        } catch(TransactionRequiredException e) {
            throw new NonTransientDataAccessResourceException("Unable to update pokemon due to database access failure.", e);
        }
    }
}
