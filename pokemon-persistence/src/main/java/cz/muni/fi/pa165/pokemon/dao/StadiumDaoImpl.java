package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
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
        if(id == null) {
            throw new NullPointerException("Id is null");
        }
        return entityManager.find(Stadium.class, id);
    }

    @Override
    public void create(Stadium stadium) {
        if(stadium == null) {
            throw new NullPointerException("Stadium is null");
        }
        entityManager.persist(stadium);
    }
    
    @Override
    public void update(Stadium stadium) {
        if(stadium == null) {
            throw new NullPointerException("Stadium is null");
        }
        entityManager.merge(stadium);
    }

    @Override
    public void delete(Stadium stadium) {
        if(stadium == null) {
            throw new NullPointerException("Stadium is null");
        }
        entityManager.remove(entityManager.find(Stadium.class, stadium.getId()));
    }

    @Override
    public List<Stadium> findAll() {
        return entityManager.createQuery("SELECT s FROM Stadium s", Stadium.class)
                .getResultList();
    }

    @Override
    public Stadium findByCity(String city) {
        if(city == null) {
            throw new NullPointerException("City is null");
        }
        try{
            return entityManager.createQuery("SELECT s FROM Stadium s WHERE s.city = :c", Stadium.class)
                .setParameter("c", city)
                .getSingleResult();
        }catch(NoResultException noResult) {
            return null;
        }
    }

    @Override
    public Stadium findByStadiumLeader(Trainer leader) {
        if(leader == null) {
            throw new NullPointerException("Trainer is null");
        }
        try{
            return entityManager.createQuery("SELECT s FROM Stadium s WHERE s.leader = :l", Stadium.class)
                .setParameter("l", leader)
                .getSingleResult();
        }catch(NoResultException noResult) {
            return null;
        }
    }

    @Override
    public List<Stadium> findByPokemonType(PokemonType type) {
        if(type == null) {
            throw new NullPointerException("Type is null");
        }
        return entityManager.createQuery("SELECT s FROM Stadium s WHERE s.type = :t", Stadium.class)
                .setParameter("t", type)
                .getResultList();
    }
    
}
