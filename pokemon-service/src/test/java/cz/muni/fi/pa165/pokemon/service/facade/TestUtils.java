package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.BadgeDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Sabo
 */
public class TestUtils {

    public static TrainerDTO createTrainerDTO(Trainer trainer) {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setId(trainer.getId());
        trainerDTO.setName(trainer.getName());
        trainerDTO.setSurname(trainer.getSurname());
        trainerDTO.setDateOfBirth(trainer.getDateOfBirth());
        return trainerDTO;
    }

    public static BadgeDTO createBadgeDTO(Badge badge) {

        BadgeDTO badgeDTO = new BadgeDTO();
        badgeDTO.setStadiumId(badge.getStadium().getId());
        badgeDTO.setTrainerId(badge.getTrainer().getId());
        badgeDTO.setId(badge.getId());
        return badgeDTO;
    }

    public static List<BadgeDTO> createBadgeDTOList(List<Badge> badges) {

        List<BadgeDTO> badgeDTOList = new ArrayList<>();
        for (Badge badge : badges) {
            badgeDTOList.add(createBadgeDTO(badge));
        }
        return badgeDTOList;
    }

    public static StadiumDTO createStadiumDTO(Stadium stadium) {
        StadiumDTO stadiumDTO;
        stadiumDTO = new StadiumDTO();
        stadiumDTO.setId(stadium.getId());
        stadiumDTO.setCity(stadium.getCity());
        stadiumDTO.setType(stadium.getType());
        stadiumDTO.setStadiumLeaderId(stadium.getLeader().getId());
        return stadiumDTO;
    }
    
    public static PokemonDTO createPokemonDTO(Pokemon pokemon) {
        PokemonDTO pokemonDTO = new PokemonDTO();
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setNickname(pokemon.getNickname());
        pokemonDTO.setSkillLevel(pokemon.getSkillLevel());
        pokemonDTO.setType(pokemon.getType());
        return pokemonDTO;
    }
}
