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

import cz.muni.fi.pa165.pokemon.entity.Trainer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * @author Milos Bartak
 */
@Repository
public class TrainerDaoImpl implements TrainerDao{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Trainer findById(Long id) {
        return entityManager.find(Trainer.class, id);
    }

    @Override
    public void create(Trainer trainer) {
        entityManager.persist(trainer);
    }

    @Override
    public void delete(Trainer trainer) {
        entityManager.remove(trainer);
    }

    @Override
    public List<Trainer> findAll() {
        return entityManager.createQuery("SELECT t FROM Trainer t", Trainer.class)
                .getResultList();
    }

    @Override
    public Trainer findByName(String name, String surname) {
        try{
            return entityManager.createQuery("SELECT t FROM Trainer where name=:n", Trainer.class)
                .setParameter(":n", name)
                .getSingleResult();
        }catch(NoResultException noResult) {
            return null;
        }
    }
    
}
