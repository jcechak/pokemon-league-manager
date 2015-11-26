package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.StadiumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Implementation of StadiumFacade that uses service layer to access entities.
 * @author Dominika Talianova
 */
@Service
@Transactional
public class StadiumFacadeImpl implements StadiumFacade {

    @Inject
    private StadiumService stadiumService;

    @Inject
    private MappingService mappingService;

    @Override
    public void createStadium(StadiumDTO stadiumDTO){
        Stadium stadiumEntity = mappingService.map(stadiumDTO,Stadium.class);
        stadiumService.createStadium(stadiumEntity);
        stadiumDTO.setId(stadiumEntity.getId());
    }

    @Override
    public void updateStadium(StadiumDTO stadiumDTO){
        if(stadiumDTO == null){
            throw new IllegalArgumentException("stadiumDTO is null.");
        }
        stadiumService.updateStadium(mappingService.map(stadiumDTO, Stadium.class));
    }

    @Override
    public void deleteStadium(StadiumDTO stadiumDTO){
        if(stadiumDTO == null){
            throw new IllegalArgumentException("stadiumDTO is null.");
        }
        stadiumService.deleteStadium(mappingService.map(stadiumDTO, Stadium.class));
    }

    @Override
    public StadiumDTO findById(Long id){
        if(id == null){
            throw new IllegalArgumentException("ID is null.");
        }
        Stadium stadiumEntity = stadiumService.getStadiumById(id);
        return mappingService.map(stadiumEntity, StadiumDTO.class);
    }

    @Override
    public Collection<StadiumDTO> findAll(){
        return mappingService.map(stadiumService.getAll(), StadiumDTO.class);
    }

    @Override
    public Collection<StadiumDTO> findByType(PokemonType type){
        if(type == null){
            throw new IllegalArgumentException("type is null.");
        }
        Collection<Stadium> stadiums = stadiumService.findByType(type);
        return mappingService.map(stadiums, StadiumDTO.class);
    }

    @Override
    public StadiumDTO findByCity(String city){
        if(city == null){
            throw new IllegalArgumentException("city is null.");
        }
        Stadium stadiumEntity = stadiumService.findByCity(city);
        return mappingService.map(stadiumEntity, StadiumDTO.class);
    }

    @Override
    public StadiumDTO findStadiumByLeader(TrainerDTO leader){
        if(leader == null){
            throw new IllegalArgumentException("leader is null.");
        }
        Stadium stadiumEntity = stadiumService.findByLeader(mappingService.map(leader, Trainer.class));
        return mappingService.map(stadiumEntity, StadiumDTO.class);
    }

    @Override
    public TrainerDTO getTheLeader(StadiumDTO stadiumDTO){
        if(stadiumDTO == null){
            throw new IllegalArgumentException("stadium is null.");
        }
        Stadium tempStadium = mappingService.map(stadiumDTO, Stadium.class);
        Trainer tempTrainer = stadiumService.getTheLeader(tempStadium);
        return mappingService.map(tempTrainer, TrainerDTO.class);
    }

    @Override
    public boolean hasLeader(StadiumDTO stadiumDTO){
        if (stadiumDTO == null){
            throw new IllegalArgumentException("stadium is null.");
        }
        Stadium tempStadium = mappingService.map(stadiumDTO, Stadium.class);
        return stadiumService.hasLeader(tempStadium);
    }

}
