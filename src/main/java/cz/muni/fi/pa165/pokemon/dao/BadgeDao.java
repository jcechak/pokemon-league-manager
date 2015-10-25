package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import java.util.List;

/**
 * Interface describing the contract of a entity managing DAO.
 *
 * @author Jaroslav Cechak
 */
public interface BadgeDao {

    /**
     * Creates a managed entity in the database.
     *
     * @param badge to be created, must not be null
     */
    void create(Badge badge);

    /**
     * Updates values of entity in the database.
     *
     * @param badge to be updated, must not be null
     */
    void update(Badge badge);

    /**
     * Deletes entity form the database.
     *
     * @param badge entity to be deleted, must not be null and must be managed
     */
    void delete(Badge badge);

    /**
     * Finds entity with the given id.
     *
     * @param id of entity to be retrieved, must not be null.
     * @return @link{ cz.muni.fi.pa165.pokemon.entity.Badge badge} with the
     * given id or null if there is no entity with such id
     */
    Badge findById(Long id);

    /**
     * Finds all badges stored in the database.
     *
     * @return @link{ java.util.List list} of all badges currently in the
     * database, list is empty if there is no such badge (list is never null)
     */
    List<Badge> findAll();

    /**
     * Finds all badges received by given trainer.
     *
     * @param trainer that received badges, must not be null
     * @return @link{ java.util.List list} of all badges received by trainer,
     * list is empty if there is no such badge (list is never null)
     */
    List<Badge> findAllWithTrainer(Trainer trainer);

    /**
     * Finds all badges issued in the given stadium
     *
     * @param stadium that issued badges, must not be null
     * @return @link{ java.util.List list} of all badges issued by stadium, list
     * is empty if there is no such badge (list is never null)
     */
    List<Badge> findAllWithStadium(Stadium stadium);

    /**
     * Finds badge that has been issued by given stadium to the given trainer.
     *
     * @param trainer that received a badge, must not be null
     * @param stadium that issued a badge, must not be null
     * @return @link{ cz.muni.fi.pa165.pokemon.entity.Badge badge} that was
     * issued by stadium to trainer
     */
    Badge findByTrainerAndStadium(Trainer trainer, Stadium stadium);

}
