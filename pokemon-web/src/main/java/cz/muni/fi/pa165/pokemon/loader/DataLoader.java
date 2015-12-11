package cz.muni.fi.pa165.pokemon.loader;

import cz.muni.fi.pa165.pokemon.entity.Badge;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.service.BadgeService;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
import cz.muni.fi.pa165.pokemon.service.StadiumService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Milos Bartak
 */
@Component
@Transactional
public class DataLoader {
    private Trainer trainer1;
    private Trainer trainer2;
    private Trainer leader1;
    private Trainer leader2;
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Pokemon pokemon3;
    private Pokemon pokemon4;
    private Badge badge1;
    private Badge badge2;
    private Stadium stadium1;
    private Stadium stadium2;
    
    @Autowired
    private TrainerService trainerService;
    
    @Autowired
    private PokemonService pokemonService;
    
    @Autowired
    private BadgeService badgeService;
    
    @Autowired
    private StadiumService stadiumService;
    
    public void loadData() {
        //trainer save
        trainer1 = new Trainer();
        trainer1.setDateOfBirth(new Date(1997-01-21));
        trainer1.setName("John");
        trainer1.setSurname("Cena");
        trainer1.setStadium(null);
        //trainer1.addPokemon(pokemon1);
        //trainer1.addBadge(badge1);
        
        trainer2 = new Trainer();
        trainer2.setDateOfBirth(new Date(1992-01-21));
        trainer2.setName("Karel");
        trainer2.setSurname("Cena");
        trainer2.setStadium(null);
        //trainer2.addPokemon(pokemon2);
        //trainer2.addBadge(badge2);
        
        leader1 = new Trainer();
        leader1.setDateOfBirth(new Date(1942-01-21));
        leader1.setName("Brock");
        leader1.setSurname("Brock");
        //leader1.setStadium(stadium1);
        //leader1.addPokemon(pokemon3);
        
        leader2 = new Trainer();
        leader2.setDateOfBirth(new Date(1942-01-11));
        leader2.setName("Misty");
        leader2.setSurname("Mistic");
        //leader2.setStadium(stadium2);
        //leader2.addPokemon(pokemon4);
        
        trainerService.createTrainer(trainer1);
        trainerService.createTrainer(trainer2);
        trainerService.createTrainer(leader1);
        trainerService.createTrainer(leader2);
        
        //pokemon save
        pokemon1 = new Pokemon();
        pokemon1.setName("Pikachu");
        pokemon1.setNickname("Chu");
        pokemon1.setSkillLevel(5);
        pokemon1.setTrainer(trainer1);
        pokemon1.setType(PokemonType.ELECTRIC);
        
        pokemon2 = new Pokemon();
        pokemon2.setName("Gengar");
        pokemon2.setNickname("Scary");
        pokemon2.setSkillLevel(15);
        pokemon2.setTrainer(trainer2);
        pokemon2.setType(PokemonType.GHOST);
        
        pokemon3 = new Pokemon();
        pokemon3.setName("Onix");
        pokemon3.setNickname("nix");
        pokemon3.setSkillLevel(3);
        pokemon3.setTrainer(leader1);
        pokemon3.setType(PokemonType.ROCK);
        
        pokemon4 = new Pokemon();
        pokemon4.setName("Psyduck");
        pokemon4.setNickname("Psycho");
        pokemon4.setSkillLevel(7);
        pokemon4.setTrainer(leader2);
        pokemon4.setType(PokemonType.PHYSIC);
        
        pokemonService.createPokemon(pokemon1);
        pokemonService.createPokemon(pokemon2);
        pokemonService.createPokemon(pokemon3);
        pokemonService.createPokemon(pokemon4);
        
        //stadium save
        stadium1 = new Stadium();
        stadium1.setCity("Brno");
        stadium1.setType(PokemonType.ROCK);
        stadium1.setLeader(leader1);
        
        stadium2 = new Stadium();
        stadium2.setCity("Pallet");
        stadium2.setType(PokemonType.DRAGON);
        stadium2.setLeader(leader2);

        stadiumService.createStadium(stadium1);
        stadiumService.createStadium(stadium2);
        
        //badge save
        badge1 = new Badge();
        badge1.setStadium(stadium1);
        badge1.setTrainer(trainer1);
        
        badge2 = new Badge();
        badge2.setStadium(stadium2);
        badge2.setTrainer(trainer2);
        
        badgeService.createBadge(badge1);
        badgeService.createBadge(badge2);
    }
}

