/*
 * Copyright (C) 2015 MiloS
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Milos Bartak
 */
@Repository
public class StadiumDaoImp implements StadiumDao {

    @PersistenceContext
    private EntityManager entityManager;
     
    @Override
    public Stadium findById(Long id) {
        return entityManager.find(Stadium.class, id);
    }

    @Override
    public void create(Stadium stadium) {
        entityManager.persist(stadium);
    }

    @Override
    public void delete(Stadium stadium) {
        entityManager.remove(stadium);
    }

    @Override
    public List<Stadium> findAll() {
        return entityManager.createQuery("SELECT s FROM Stadium s", Stadium.class)
                .getResultList();
    }

    @Override
    public Stadium findByCity(String city) {
        try{
            return entityManager.createQuery("SELECT s FROM Stadium s where city = :c", Stadium.class)
                .setParameter(":c", city)
                .getSingleResult();
        }catch(NoResultException noResult) {
            return null;
        }
    }

    @Override
    public Stadium findByStadiumLeader(Trainer leader) {
        try{
            return entityManager.createQuery("SELECT s FROM Stadium s where leader = :l", Stadium.class)
                .setParameter(":l", leader)
                .getSingleResult();
        }catch(NoResultException noResult) {
            return null;
        }
    }

    @Override
    public List<Stadium> findByPokemonType(PokemonType type) {
        return entityManager.createQuery("SELECT s FROM Stadium s where type = :t", Stadium.class)
                .setParameter(":t", type)
                .getResultList();
    }
    
}
