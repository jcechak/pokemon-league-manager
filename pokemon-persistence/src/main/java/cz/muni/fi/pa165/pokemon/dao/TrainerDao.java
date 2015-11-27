package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Trainer;

import java.util.List;

/**
 *
 * @author Dominika Talianova
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

    /**
     * finds all trainers with the same name
     * @param name the name by which the search for the trainer is done
     * @return list of trainers with given name
     */
    List<Trainer> findAllTrainersWithName(String name);

    /**
     * finds all trainers with the same surname
     * @param surname the surname by which the search for the trainer is done
     * @return list of trainers with given surname
     */
    List<Trainer> findAllTrainersWithSurname(String surname);
}
