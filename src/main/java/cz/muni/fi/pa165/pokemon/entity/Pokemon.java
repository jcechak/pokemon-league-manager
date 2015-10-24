package cz.muni.fi.pa165.pokemon.entity;

import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * This class corresponds to an entity pokemon.
 *
 * @author Jaroslav Cechak
 */
@Entity
public class Pokemon {

     // Attributes
    /**
     * Automaticaly generated ID used as primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of a pokemon (e.g. Pikachu)
     */
    @NotNull
    @Column(nullable = false)
    private String name;

    /**
     * Nickname of a pokemon (e.g. Karel)
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String nickname;

    /**
     * Type of a pokemon, this determines his abilities and effectiveness against
     * other pokemons.
     */
    @NotNull
    @Column(nullable = false)
    private PokemonType type;

    /**
     * The level of pokemon's skills, this determines his strength.
     */
    @Column
    private int level;

    /**
     * Trainer (owner) of a pokemon
     */
    @OneToMany
    private Trainer trainer;

    // Constructors
    public Pokemon() {
    }

    // Getters and getters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    // Equals and hashcode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.nickname);
        return hash;
    }

     // Business equivalence on name and nickname
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Pokemon)) {
            return false;
        }
        final Pokemon other = (Pokemon) obj;
        if (!Objects.equals(this.name, other.getName())) {
            return false;
        }
        if (!Objects.equals(this.nickname, other.getNickname())) {
            return false;
        }
        return true;
    }

}
