package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.exceptions.PokemonServiceException;
import cz.muni.fi.pa165.pokemon.dao.BadgeDao;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of Badge service interface.
 * @author Marek Sabo
 */
@Service
public class BadgeServiceImpl implements BadgeService {

    @Inject
    private BadgeDao badgeDao;


    @Override
    public void createBadge(Badge badge) {
        if (badge.getStadium().getLeader().equals(badge.getTrainer())) {
            throw new PokemonServiceException("Badge assign to leader of the stadium.");
        }
        badgeDao.create(badge);
    }

    @Override
    public void updateBadge(Badge badge) {
        badgeDao.update(badge);
    }

    @Override
    public void deleteBadge(Badge badge) {
        badgeDao.delete(badge);
    }

    @Override
    public Badge findBadgeById(Long id) {
        return badgeDao.findById(id);
    }

    @Override
    public List<Badge> getAllBadges() {
        return Collections.unmodifiableList(badgeDao.findAll());
    }

    @Override
    public List<Badge> getBadgesWithTrainer(Trainer trainer) {
        return Collections.unmodifiableList(badgeDao.findAllWithTrainer(trainer));
    }

    @Override
    public List<Badge> getBadgesWithStadium(Stadium stadium) {
        return Collections.unmodifiableList(badgeDao.findAllWithStadium(stadium));
    }

    @Override
    public Badge findBadgeByTrainerAndStadium(Trainer trainer, Stadium stadium) {
        return badgeDao.findByTrainerAndStadium(trainer, stadium);
    }
}
