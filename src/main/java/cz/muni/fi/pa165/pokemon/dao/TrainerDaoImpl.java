package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kasumi
 */
@Repository
@Transactional
public class TrainerDaoImpl implements TrainerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Trainer trainer) {
        if(trainer == null) {
            throw new NullPointerException("Trainer cannot be null");
        }
        entityManager.persist(trainer);
    }

    @Override
    public void update(Trainer trainer) {
        if(trainer == null) {
            throw new NullPointerException("Trainer cannot be null");
        }
        entityManager.merge(trainer);
    }

    @Override
    public void delete(Trainer trainer) {
        if(trainer == null) {
            throw new NullPointerException("Trainer cannot be null");
        }
        entityManager.remove(entityManager.find(Trainer.class, trainer.getId()));
    }

    @Override
    public Trainer findById(Long id) {
        if(id == null) {
            throw new NullPointerException("Id is null");
        }
        return entityManager.find(Trainer.class, id);
    }

    @Override
    public List<Trainer> findAll() {
        return entityManager.createQuery("SELECT t FROM Trainer t", Trainer.class).getResultList();
    }
}
