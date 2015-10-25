package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Interface describing the contract of a entity managing DAO.
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
        em.remove(pokemon);
    }

    @Override
    public Pokemon findById(Long id) {
        return em.find(Pokemon.class, findById(id));
    }
}
