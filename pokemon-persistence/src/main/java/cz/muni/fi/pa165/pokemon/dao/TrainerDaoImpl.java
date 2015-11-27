package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;

/**
 *
 * @author Dominika Talianova
 */
@Repository
@Transactional
public class TrainerDaoImpl implements TrainerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Trainer trainer) {
        try {
            entityManager.persist(trainer);
            entityManager.flush();
        } catch (TransactionRequiredException | IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to create trainer due to database access failure.", e);
        } catch (ValidationException | PersistenceException e){
            throw new DataIntegrityViolationException("Unable to create trainer due to integrity violation.", e);
        } catch (IllegalArgumentException iae){
            throw new InvalidDataAccessApiUsageException("Unable to create object, because received object is not an entity.", iae);        }

    }

    @Override
    public void update(Trainer trainer) {
        try {
            entityManager.merge(trainer);
            entityManager.flush();
        } catch (TransactionRequiredException | IllegalStateException e){
            throw new NonTransientDataAccessResourceException("Unable to update trainer due to database access failure.", e);
        } catch (ValidationException | PersistenceException e){
            throw new DataIntegrityViolationException("Unable to update trainer due to integrity violation.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to update object, because received object is not an entity.", iae);
        }
    }

    @Override
    public void delete(Trainer trainer) {
        try {
            entityManager.remove(entityManager.find(Trainer.class, trainer.getId()));
            entityManager.flush();
        } catch (TransactionRequiredException | IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to delete trainer due to database access failure.", e);
        } catch (ValidationException | PersistenceException e) {
            throw new DataIntegrityViolationException("Unable to delete trainer due to integrity violation.", e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to delete object, because required object is not an entity.", iae);
        }
    }

    @Override
    public Trainer findById(Long id) {
        try {
            return entityManager.find(Trainer.class, id);
        } catch (IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to retrieve trainer due to database access failure.", e);
        } catch (IllegalArgumentException iae){
            throw new InvalidDataAccessApiUsageException("Unable to retrieve object, because received key is not valid primary key.", iae);
        }

    }

    @Override
    public List<Trainer> findAll() {
        try {
            return entityManager.createQuery("SELECT t FROM Trainer t", Trainer.class).getResultList();
        } catch (IllegalStateException e) {
            throw new NonTransientDataAccessResourceException("Unable to retrieve list due to database access failure.",e);
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve list due to internal error.", iae);
        }
    }

    @Override
    public List<Trainer> findAllTrainersWithPokemon(Pokemon pokemon){
        try{
            return entityManager.createQuery("SELECT t FROM Trainer t WHERE t.pokemon = :t", Trainer.class)
                    .setParameter("t",pokemon)
                    .getResultList();
        } catch(NoResultException noResult) {
            return null;
        } catch(IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve list because given object is not an entity.", iae);
        } catch (PersistenceException e) {
            throw new DataRetrievalFailureException("Unable to retrieve data with selected query", e);
        }
    }

    @Override
    public List<Trainer> findAllTrainersWithBadge(Badge badge){
        try{
            return entityManager.createQuery("SELECT t FROM Trainer t WHERE t.badge = :t", Trainer.class)
                    .setParameter("t",badge)
                    .getResultList();
        } catch (NoResultException noResult) {
            return null;
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve list because given object is not an entity.", iae);
        } catch (PersistenceException e) {
            throw new DataIntegrityViolationException("Unable to retrieve data with selected query.", e);
        }

    }

    @Override
    public List<Trainer> findAllTrainersWithName(String name){
        try{
            return entityManager.createQuery("SELECT t FROM Trainer t WHERE t.name = :t", Trainer.class)
                    .setParameter("t",name)
                    .getResultList();
        } catch (NoResultException noResult){
            return null;
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve list because the given parameter is not valid.", iae);
        } catch (PersistenceException e) {
            throw new DataIntegrityViolationException("Unable to retrieve data with selected query.", e);
        }

    }

    @Override
    public List<Trainer> findAllTrainersWithSurname(String surname){
        try{
            return entityManager.createQuery("SELECT t FROM Trainer t WHERE t.surname = :t", Trainer.class)
                    .setParameter("t",surname)
                    .getResultList();
        } catch (NoResultException noResult) {
            return null;
        } catch (IllegalArgumentException iae) {
            throw new InvalidDataAccessApiUsageException("Unable to retrieve list because the given parameter is not valid.", iae);
        } catch (PersistenceException e){
            throw new DataIntegrityViolationException("Unable to retrieve data with given query.",e);
        }
    }
}
