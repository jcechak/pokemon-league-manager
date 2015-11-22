package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Badge} entity.
 * @author Marek Sabo
 */
public interface BadgeService {

    /**
     * Creates the Badge in the database.
     *
     * @param Badge the Badge to be persisted
     */
    void createBadge(Badge Badge);

    /**
     * Updates the Badge record in the database.
     *
     * @param Badge the Badge with new attribute values
     */
    void updateBadge(Badge Badge);

    /**
     * Deletes the Badge from the database.
     *
     * @param Badge the Badge to be deleted
     */
    void deleteBadge(Badge Badge);

    /**
     * Gets Badge with given id.
     *
     * @param id the id of the Badge
     * @return the Badge with the given id
     */
    Badge getBadgeById(Long id);

    /**
     * Retrieves all the badges from the database.
     *
     * @return List of all badges currently in the database
     */
    List<Badge> getAllBadges();


    /**
     * Retrieves the Badge with selected trainer.
     * @param trainer trainer to which the badge belongs to
     * @return all badges which the trainer owns
     */
    List<Badge> getBadgesWithTrainer(Trainer trainer);

    /**
     * Retrieves the Badge with selected stadium.
     * @param stadium stadium where the badge was acquired
     * @return all badges from the stadium
     */
    List<Badge> getBadgesWithStadium(Stadium stadium);

    /**
     * Retrieves the Badge with selected trainer and stadium.
     * @param trainer trainer to which the badge belongs to
     * @param stadium stadium where the badge was acquired
     * @return unique Badge
     */
    Badge getBadgeWithTrainerAndStadium(Trainer trainer, Stadium stadium);

}
