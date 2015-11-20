package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import java.sql.Date;
import java.util.Collection;
import javax.inject.Inject;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Milos Bartak
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class TrainerFacadeImplNGTest extends AbstractTestNGSpringContextTests{
    
    @Inject
    TrainerFacade trainerFacade;
    
    @Inject
    TrainerService trainerService;
    
    @Inject
    PokemonService pokemonService;
    
    @Inject
    private MappingService beanMappingService;
    
    private Trainer setUpTrainer;
    //private TrainerDTO setUpTrainerDTO;
    
    //persist stadium
    private Stadium stadium = null;
    
    @BeforeMethod
    public void setUpMethod() {
        
        setUpTrainer = new Trainer();
        setUpTrainer.setName("Brock");
        setUpTrainer.setSurname("Brokovnice");
        setUpTrainer.setStadium(stadium);
        setUpTrainer.setDateOfBirth(Date.valueOf("1989-10-11"));
        trainerService.createTrainer(setUpTrainer);
        
        beanMappingService.map(setUpTrainer, TrainerDTO.class);
    }
    
    @AfterMethod
    public void tearDownMethod() {
        Collection<Trainer> col = trainerService.findAllTrainers();
        for(Trainer t : col) {
            trainerService.deleteTrainer(t);
        }
    }
    
    @Test
    public void testCreateTrainer() {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setName("Ash");
        trainerDTO.setSurname("Hsa");
        trainerDTO.setStadium(null);
        trainerDTO.setDateOfBirth(Date.valueOf("1990-10-11"));
        
        trainerFacade.createTrainer(trainerDTO);

        assertEquals(beanMappingService.map(trainerDTO, Trainer.class), trainerService.findTrainerById(trainerDTO.getId()), trainerDTO.toString() + " not created");
    }
    
    @Test
    public void testDeleteTrainer() {
        trainerFacade.deleteTrainer(beanMappingService.map(setUpTrainer, TrainerDTO.class));
        
        assertEquals(null, trainerService.findTrainerById(setUpTrainer.getId()), "Trainer was not deleted properly");
    }
    
    @Test
    public void testUpdateTrainer() {
        setUpTrainer.setName("Brockbrock");
        
        trainerFacade.updateTrainer(beanMappingService.map(setUpTrainer, TrainerDTO.class));
        
        assertEquals(setUpTrainer.getName(), trainerService.findTrainerById(setUpTrainer.getId()).getName());
    }
    
    /*@Test
    public void testIsLeadeOfTheStadium() {
        trainerFacade.isLeaderOfTheStadium(beanMappingService.map(setUpTrainer, TrainerDTO.class), beanMappingService.map(stadium, StadiumDTO.class));
    }*/
    
    /*@Test
    public void testAddPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Pika");
        pokemon.setNickname("Chu");
        pokemon.setSkillLevel(77);
        pokemon.setType(PokemonType.ELECTRIC);
        pokemon.setTrainer(setUpTrainer);
        
        pokemonService.createPokemon(pokemon);
        trainerFacade.addPokemon(beanMappingService.map(setUpTrainer, TrainerDTO.class), beanMappingService.map(pokemon, PokemonDTO.class));
        
        assertEquals(1, setUpTrainer.getPokemons().size(), "pokemon not added");
    }*/
    
    
    @Test
    public void testFindTrainerById() {
        Long id = setUpTrainer.getId();
        
        TrainerDTO found = trainerFacade.findTrainerById(id);
        
        assertEquals(found, beanMappingService.map(setUpTrainer, TrainerDTO.class), setUpTrainer.toString() + " not found");
    }
    
    @Test
    public void testFindAllTrainers() {
        Trainer trainer = new Trainer();
        trainer.setName("Koren");
        trainer.setSurname("Borec");
        trainer.setStadium(null);
        trainer.setDateOfBirth(Date.valueOf("1949-10-11"));
        trainerService.createTrainer(trainer);
        
        assertEquals(2, trainerFacade.findAllTrainers().size());
    }
    
    /*@Test
    public void testFindAllTrainersWithName() {
        Trainer trainer = new Trainer();
        trainer.setName("Brock");
        trainer.setSurname("Borec");
        trainer.setStadium(null);
        trainer.setDateOfBirth(Date.valueOf("1949-10-11"));
        trainerService.createTrainer(trainer);
        
        assertEquals(2, trainerFacade.findAllTrainersWithName("Brock").size());
    }
    
    @Test
    public void testFindAllTrainersWithSurName() {
        Trainer trainer = new Trainer();
        trainer.setName("Borec");
        trainer.setSurname("Brokovnice");
        trainer.setStadium(null);
        trainer.setDateOfBirth(Date.valueOf("1949-10-11"));
        trainerService.createTrainer(trainer);
        
        assertEquals(2, trainerFacade.findAllTrainersWithSurname("Brokovnice").size());
    }*/
}
