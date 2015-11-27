package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.StadiumService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Testing class for StadiumFacade.
 * @author Dominika Talianova
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class StadiumFacadeImplNGTest extends AbstractTestNGSpringContextTests {

    @Inject
    private StadiumFacade stadiumFacade;

    @Inject
    private StadiumService stadiumService;

    @Inject
    TrainerService trainerService;

    @Inject
    private MappingService beanMappingService;

    private Stadium stadium1;

    private Trainer trainer1;

    @BeforeMethod
    public void beforeMethod(){
        trainer1 = new Trainer();
        trainer1.setName("Brock");
        trainer1.setSurname("Brokovnice");
        trainer1.setDateOfBirth(Date.valueOf("1989-10-11"));
        trainerService.createTrainer(trainer1);

        stadium1 = new Stadium();
        stadium1.setCity("Azalea");
        stadium1.setLeader(trainer1);
        stadium1.setType(PokemonType.FIRE);
        stadiumService.createStadium(stadium1);

        beanMappingService.map(stadium1,StadiumDTO.class);

    }

    @AfterMethod
    public void tearDown(){
        Collection<Stadium> stadiumCollection = stadiumService.getAll();
        for(Stadium s :  stadiumCollection){
            stadiumService.deleteStadium(s);
        }
        Collection<Trainer> trainerCollection = trainerService.findAllTrainers();
        for(Trainer t : trainerCollection){
            trainerService.deleteTrainer(t);
        }
    }

    @Test
    public void testCreateStadium(){
        Trainer tempTrainer = new Trainer();
        tempTrainer.setDateOfBirth(Date.valueOf("1990-10-11"));
        tempTrainer.setName("Brock");
        tempTrainer.setSurname("Some");
        trainerService.createTrainer(tempTrainer);

        StadiumDTO stadiumDTO = new StadiumDTO();
        stadiumDTO.setType(PokemonType.FLYING);
        stadiumDTO.setCity("Orange");
        stadiumDTO.setStadiumLeaderId(tempTrainer.getId());
        stadiumFacade.createStadium(stadiumDTO);

        assertEquals(beanMappingService.map(stadiumDTO, Stadium.class), stadiumService.getStadiumById(stadiumDTO.getId())
                , "Stadium not created");
    }

    @Test
    public void testDeleteStadium(){
        stadiumFacade.deleteStadium(beanMappingService.map(stadium1, StadiumDTO.class));
        assertEquals(null, stadiumService.getStadiumById(stadium1.getId()), "The stadium was not deleted properly.");
    }

    @Test
    public void testUpdateStadium(){
        stadium1.setType(PokemonType.DRAGON);
        stadiumFacade.updateStadium(beanMappingService.map(stadium1, StadiumDTO.class));
        assertEquals(stadium1.getType(),stadiumService.getStadiumById(stadium1.getId()).getType());
    }

    @Test
    public void testFindById(){
        StadiumDTO tempStadium = stadiumFacade.findById(stadium1.getId());
        assertEquals(tempStadium,beanMappingService.map(stadium1, StadiumDTO.class),"Stadium was no found.");
    }

    @Test
    public void testGetAll(){
        Stadium stadium = new Stadium();
        Trainer trainer2 = new Trainer();
        trainer2.setName("Abrocka");
        trainer2.setSurname("Abrakavnice");
        trainer2.setDateOfBirth(Date.valueOf("1989-10-11"));
        trainerService.createTrainer(trainer2);

        stadium.setCity("Black");
        stadium.setType(PokemonType.BUG);
        stadium.setLeader(trainer2);
        stadiumService.createStadium(stadium);

        assertEquals(stadiumFacade.findAll().size(),2,"Failed to find all stadiums.");
    }

    @Test
    public void testFindBytType(){
        List<StadiumDTO> stadiumsByType = new ArrayList<>();
        stadiumsByType.add(beanMappingService.map(stadium1, StadiumDTO.class));
        assertEquals(stadiumFacade.findByType(PokemonType.FIRE), stadiumsByType, "Failed to find all stadiums by type.");
    }

    @Test
    public void testFindByCity(){
        StadiumDTO stadiumsByCity;
        stadiumsByCity = beanMappingService.map(stadium1, StadiumDTO.class);
        assertEquals(stadiumFacade.findByCity("Azalea"),stadiumsByCity, "Failed to find all stadiums by city.");
    }

    @Test
    public void testFindByLeader(){
        StadiumDTO tempStadium = stadiumFacade.findStadiumByLeader(beanMappingService.map(trainer1, TrainerDTO.class));
        assertEquals(beanMappingService.map(stadium1, StadiumDTO.class),tempStadium, "Failed to find the stadium by leader.");
    }


    @Test
    public void testGetTheLeader(){
        StadiumDTO tempStadium = beanMappingService.map(stadium1, StadiumDTO.class);
        assertEquals(stadiumFacade.getTheLeader(tempStadium),beanMappingService.map(trainer1,TrainerDTO.class)
                ,"Failed to retrieve the leader.");
    }

    @Test
    public void testHasLeader(){
        StadiumDTO tempStadium = beanMappingService.map(stadium1, StadiumDTO.class);
        if(!stadiumFacade.hasLeader(tempStadium)){
            fail("The stadium does not have a stadium leader.");
        }
    }


}
