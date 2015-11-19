package cz.muni.fi.pa165.pokemon.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Milos Bartak
 */
public class TrainerDTO {

    private Long id;

    private String name;

    private String surname;
    
    private Date dateOfBirth;

    private final List<PokemonDTO> pokemons = new ArrayList<PokemonDTO>();

    private final List<BadgeDTO> badges = new ArrayList<BadgeDTO>();

    private StadiumDTO stadium;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public StadiumDTO getStadium() {
        return stadium;
    }

    public void setStadium(StadiumDTO stadium) {
        this.stadium = stadium;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void addPokemon(PokemonDTO pokemon) {
        pokemons.add(pokemon);
    }

    public List<PokemonDTO> getPokemons() {
        return Collections.unmodifiableList(pokemons);
    }

    public void addBadge(BadgeDTO badge) {
        badges.add(badge);
    }

    public List<BadgeDTO> getBadges() {
        return Collections.unmodifiableList(badges);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.surname);
        hash = 53 * hash + Objects.hashCode(this.dateOfBirth);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TrainerDTO)) {
            return false;
        }
        final TrainerDTO other = (TrainerDTO) obj;
        if (!Objects.equals(this.name, other.getName())) {
            return false;
        }
        if (!Objects.equals(this.surname, other.getSurname())) {
            return false;
        }
        if (this.dateOfBirth.compareTo(other.getDateOfBirth())!=0) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Trainer{"
                + "id=" + id
                + ", name=" + name
                + ", surname=" + surname
                + ", dateOfBirth=" + dateOfBirth
                + '}';
    }
}
