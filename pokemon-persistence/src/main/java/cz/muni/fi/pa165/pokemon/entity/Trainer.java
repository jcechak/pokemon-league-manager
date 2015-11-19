package cz.muni.fi.pa165.pokemon.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


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
    private Date dateOfBirth;

    /**
     * List of trainer's pokemons
     */
    @NotNull
    @OneToMany(mappedBy = "trainer")
    private final List<Pokemon> pokemons = new ArrayList<Pokemon>();

    /**
     * List of trainer's badges obtained by defeating gym leaders
     */
    @NotNull
    @OneToMany(mappedBy = "trainer")
    private final List<Badge> badges = new ArrayList<Badge>();

    /**
     * Name of the stadium the trainer is leader of (may be null)
     */
    @OneToOne(mappedBy = "leader")
    private Stadium stadium;

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
