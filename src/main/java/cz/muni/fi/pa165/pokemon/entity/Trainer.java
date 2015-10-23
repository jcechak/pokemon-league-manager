package cz.muni.fi.pa165.pokemon.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.NotNull;

/**
 * @author Milos Bartak
 */
@Entity
public class Trainer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Name of the trainer
     */
    @NotNull
    @Column(nullable = false)
    private String name;
    
    /**
     * Surname of the trainer
     */
    @NotNull
    @Column(nullable = false)
    private String surname;
    
    /**
     * Birth date of the trainer
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    
    /**
     * List of trainer's pokemons
     */
    @NotNull
    @OneToMany
    private List<Pokemon> pokemons = new ArrayList<Pokemon>();
    
    /**
     * List of trainer's badges obtained by defeating gym leaders
     */
    @NotNull
    @OneToMany
    private List<Badge> badges = new ArrayList<Badge>();
    
    /**
     * Name of the stadium the trainer is leader of (may be null)
     */
    private Stadium stadium;

    
    //Getters and setters
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

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
    }
    
    public List<Pokemon> getPokemons() {
        return Collections.unmodifiableList(pokemons);
    }
    
    public void addBadge(Badge badge) {
        badges.add(badge);
    }
    
    public List<Badge> getBadges() {
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

    // Business equivalence on name, surname and date of birth
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Trainer)) {
            return false;
        }
        final Trainer other = (Trainer) obj;
        if (!Objects.equals(this.name, other.getName())) {
            return false;
        }
        if (!Objects.equals(this.surname, other.getSurname())) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.getDateOfBirth())) {
            return false;
        }
        return true;
    }
    
    
}
