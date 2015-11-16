package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
        em.persist(pokemon);
    }

    @Override
    public void update(Pokemon pokemon) {
        em.merge(pokemon);
    }

    @Override
    public void delete(Pokemon pokemon) {
        em.remove(findById(pokemon.getId()));
    }

    @Override
    public Pokemon findById(Long id) {
        return em.find(Pokemon.class, id);
    }

    @Override
    public List<Pokemon> findAll() {
        return em.createQuery("SELECT p FROM Pokemon p", Pokemon.class).getResultList();
    }

    @Override
    public List<Pokemon> findAllWithTrainer(Trainer trainer) {
        return em.createQuery("SELECT p FROM Pokemon p WHERE p.trainer = :t", Pokemon.class)
                .setParameter("t", trainer)
                .getResultList();
    }
}
