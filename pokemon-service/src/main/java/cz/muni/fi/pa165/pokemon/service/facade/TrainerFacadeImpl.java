package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import java.util.Collection;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the TrainerFacade that uses Service layer to acces entities.
 * 
 * @author Milos Bartak
 */
@Service
@Transactional
public class TrainerFacadeImpl implements TrainerFacade {
    
    @Inject
    private TrainerService trainerService;
    
    @Inject
    private MappingService beanMappingService;
    
    @Override
    public void createTrainer(TrainerDTO trainerDTO) {
        Trainer trainerEntity = beanMappingService.map(trainerDTO, Trainer.class);
        trainerService.createTrainer(trainerEntity);
        trainerDTO.setId(trainerEntity.getId());
    }

    @Override
    public void deleteTrainer(TrainerDTO trainerDTO) {
        trainerService.deleteTrainer(beanMappingService.map(trainerDTO, Trainer.class));
    }

    @Override
    public void updateTrainer(TrainerDTO trainerDTO) {
        trainerService.updateTrainer(beanMappingService.map(trainerDTO, Trainer.class));
    }

    @Override
    public boolean isLeaderOfTheStadium(TrainerDTO trainerDTO, StadiumDTO stadiumDTO) {
        return trainerService.isLeaderOfTheStadium(beanMappingService.map(trainerDTO, Trainer.class), beanMappingService.map(stadiumDTO, Stadium.class));
    }

    @Override
    public void addPokemon(TrainerDTO trainerDTO, PokemonDTO pokemonDTO) {
        trainerService.addPokemon(beanMappingService.map(trainerDTO, Trainer.class), beanMappingService.map(pokemonDTO, Pokemon.class));
    }

    @Override
    public void addBadge(TrainerDTO trainerDTO, BadgeDTO badgeDTO) {
        trainerService.addBadge(beanMappingService.map(trainerDTO, Trainer.class), beanMappingService.map(badgeDTO, Badge.class));
    }

    @Override
    public TrainerDTO findTrainerById(Long id) {
        Trainer trainer = trainerService.findTrainerById(id);
        return (trainer == null) ? null : beanMappingService.map(trainer, TrainerDTO.class);
    }

    @Override
    public Collection<TrainerDTO> findAllTrainers() {
        return beanMappingService.map(trainerService.findAllTrainers(), TrainerDTO.class);
    }

    //TODO spravne?
    @Override
    public Collection<TrainerDTO> findAllTrainersWithPokemon(PokemonDTO pokemonDTO) {
        return beanMappingService.map(trainerService.findAllTrainersWithPokemon(beanMappingService.map(pokemonDTO, Pokemon.class)), TrainerDTO.class);
    }

    @Override
    public Collection<TrainerDTO> findAllTrainersWithBadge(BadgeDTO badgeDTO) {
        return beanMappingService.map(trainerService.findAllTrainersWithBadge(beanMappingService.map(badgeDTO, Badge.class)), TrainerDTO.class);
    }

    @Override
    public Collection<TrainerDTO> findAllTrainersWithName(String name) {
        Collection<Trainer> trainers = trainerService.findAllTrainersWithName(name);
        return (trainers == null) ? null : beanMappingService.map(trainers, TrainerDTO.class);
    }

    @Override
    public Collection<TrainerDTO> findAllTrainersWithSurname(String surname) {
        Collection<Trainer> trainers = trainerService.findAllTrainersWithSurname(surname);
        return (trainers == null) ? null : beanMappingService.map(trainers, TrainerDTO.class);
    }

    
    
}
