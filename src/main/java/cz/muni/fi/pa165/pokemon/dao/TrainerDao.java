/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Trainer;

import java.util.List;

/**
 *
 * @author Kasumi
 */
public interface TrainerDao {
    /**
     * Creates a Trainer entry in the database.
     * @param   trainer     Trainer object that will be created
     */
    void create(Trainer trainer);

    /**
     * Updates a Trainer entry in the database.
     * @param   trainer     Trainer object that will be updated
     */
    void update(Trainer trainer);

    /**
     * Deletes a Trainer entry from the database.
     * @param   trainer     Trainer object that will be deleted
     */
    void delete(Trainer trainer);

    /**
     * Finds a Trainer entity with given id in the database
     * @param   id      ID of the Trainer to be found
     * @return  Trainer entity with given id
     */
    Trainer findById(Long id);

    /**
     * Gives the list of all Trainers in the database
     * @return  List of all existing Trainers
     */
    List<Trainer> findAll();
}
