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

/**
 *
 * @author Milos Bartak
 */
public interface StadiumDao {
    /**
     * Searches for the stadium with given id
     * @param id trainers id
     * @return the stadium with id equal to param id or null if no stadium with that id exists
     */
    public Stadium findById(Long id);
    
    /**
     * Creates new stadium
     * @param stadium the stadium that has to be created
     */
    public void create(Stadium stadium);
    
    /**
     * Updates the stadium
     * @param stadium stadium that has to be updayed
     */
    public void update(Stadium stadium);
    
    /**
     * Deletes a stadium
     * @param stadium the stadium that has to be deleted
     */
    public void delete(Stadium stadium);
    
    /**
     * Searches for all stadiums
     * @return the list of stadiums
     */
    public List<Stadium> findAll();
    
    /**
     * Searches for the stadium placed in the city
     * @param city name of the city
     * @return stadium that is in the city
     */
    public Stadium findByCity(String city);
    
    /**
     * Searches for the stadium with given leader trainer
     * @param leader the stadium leader
     * @return stadium that with given leader trainer
     */
    public Stadium findByStadiumLeader(Trainer leader);
    
    /**
     * Searches for the stadium with given pokemon type
     * @param type the type of leaders trainer pokemon
     * @return stadium list with given pokemon type
     */
    public List<Stadium> findByPokemonType(PokemonType type);
}
