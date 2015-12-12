package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.facade.BadgeFacade;
import cz.muni.fi.pa165.pokemon.service.BadgeService;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of BadgeFacade interface.
 * @author Marek Sabo
 */
@Service
@Transactional
public class BadgeFacadeImpl implements BadgeFacade {

    @Inject
    private BadgeService badgeService;

    @Inject
    private MappingService mappingService;

    @Override
    public Long assignBadge(BadgeDTO badgeDto) {
        Badge badgeEntity = mappingService.map(badgeDto, Badge.class);
        badgeService.createBadge(badgeEntity);
        badgeDto.setId(badgeEntity.getId());
        return badgeDto.getId();
    }

    @Override
    public void removeBadge(BadgeDTO badgeDto) {
        Badge badgeEntity = mappingService.map(badgeDto, Badge.class);
        badgeService.deleteBadge(badgeEntity);
        badgeDto.setId(null);
    }

    @Override
    public void updateBadge(BadgeDTO badgeDto) {
        Badge badgeEntity = mappingService.map(badgeDto, Badge.class);
        badgeService.updateBadge(badgeEntity);
    }

    @Override
    public BadgeDTO findBadgeById(Long badgeId) {
        return mappingService.map(
                badgeService.findBadgeById(badgeId), BadgeDTO.class);
    }

    @Override
    public Collection<BadgeDTO> getAllBadges() {
        return mappingService.map(badgeService.getAllBadges(), BadgeDTO.class);
    }

    @Override
    public List<BadgeDTO> getBadgesWithTrainer(TrainerDTO trainerDto) {
        Trainer trainerEntity = mappingService.map(trainerDto, Trainer.class);
        final List<Badge> badges = badgeService.getBadgesWithTrainer(trainerEntity);
        return mappingService.map(badges, BadgeDTO.class);
    }

    @Override
    public List<BadgeDTO> getBadgesWithStadium(StadiumDTO stadiumDto) {
        Stadium stadiumEntity = mappingService.map(stadiumDto, Stadium.class);
        final List<Badge> badges = badgeService.getBadgesWithStadium(stadiumEntity);
        return mappingService.map(badges, BadgeDTO.class);
    }

    @Override
    public BadgeDTO findBadgeWithTrainerAndStadium(TrainerDTO trainerDto, StadiumDTO stadiumDto) {
        Trainer trainerEntity = mappingService.map(trainerDto, Trainer.class);
        Stadium stadiumEntity = mappingService.map(stadiumDto, Stadium.class);
        Badge badgeEntity = badgeService.findBadgeByTrainerAndStadium(trainerEntity, stadiumEntity);
        return mappingService.map(badgeEntity, BadgeDTO.class);
    }
}
