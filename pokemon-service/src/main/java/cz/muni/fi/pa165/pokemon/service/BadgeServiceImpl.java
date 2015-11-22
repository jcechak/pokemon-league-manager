package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.BadgeDao;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;

import javax.inject.Inject;
import java.util.List;

/**
 * Implementation of Badge service interface.
 * @author Marek Sabo
 */
public class BadgeServiceImpl implements BadgeService {

    @Inject
    private BadgeDao badgeDao;


    @Override
    public void createBadge(Badge badge) {
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
    public Badge getBadgeById(Long id) {
        return badgeDao.findById(id);
    }

    @Override
    public List<Badge> getAllBadges() {
        return badgeDao.findAll();
    }

    @Override
    public List<Badge> getBadgesWithTrainer(Trainer trainer) {
        return badgeDao.findAllWithTrainer(trainer);
    }

    @Override
    public List<Badge> getBadgesWithStadium(Stadium stadium) {
        return badgeDao.findAllWithStadium(stadium);
    }

    @Override
    public Badge getBadgeWithTrainerAndStadium(Trainer trainer, Stadium stadium) {
        return badgeDao.findByTrainerAndStadium(trainer, stadium);
    }
}
