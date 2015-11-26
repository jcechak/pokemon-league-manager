package cz.muni.fi.pa165.pokemon.facade;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;

import java.util.Collection;
import java.util.List;

/**
 * Facade interface defining the basic methods which are used for outer world.
 * @author Marek Sabo
 */
public interface BadgeFacade {

    /**
     * Saves the badge into the system.
     * @param badgeDto badge object to be created
     */
    void assignBadge(BadgeDTO badgeDto);

    /**
     * Removes the badge from the system.
     * @param badgeDto badge object to be deleted
     */
    void removeBadge(BadgeDTO badgeDto);


    /**
     * Updates the badge attributes.
     * @param badgeDto badge object to be deleted
     */
    void updateBadge(BadgeDTO badgeDto);


    /**
     * Gets Badge with given id.
     * @param badgeId the id of the Badge
     * @return {@link BadgeDTO} with the given id
     */
    BadgeDTO findBadgeById(Long badgeId);

    /**
     * Retrieves all the badges.
     * @return List of all {@link BadgeDTO} objects
     */
    Collection<BadgeDTO> getAllBadges();

    /**
     * Retrieves badges which selected trainer owns.
     * @param trainerDto trainer to whom the badges belongs to
     * @return all badges which the trainer owns
     */
    List<BadgeDTO> getBadgesWithTrainer(TrainerDTO trainerDto);

    /**
     * Retrieves the badge with selected stadium.
     * @param stadiumDto stadium where the badge was acquired
     * @return all badges from the stadium
     */
    List<BadgeDTO> getBadgesWithStadium(StadiumDTO stadiumDto);

    /**
     * Retrieves the badge with selected trainer and stadium.
     * @param trainerDto trainer to whom the badge belongs to
     * @param stadiumDto stadium where the badge was acquired
     * @return unique badge with chosen parameters
     */
    BadgeDTO findBadgeWithTrainerAndStadium(TrainerDTO trainerDto, StadiumDTO stadiumDto);

}
