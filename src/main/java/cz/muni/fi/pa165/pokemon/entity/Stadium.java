package cz.muni.fi.pa165.pokemon.entity;

import cz.muni.fi.pa165.enums.PokemonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * This class corresponds to entity stadium.
 * @author Marek Sabo
 */
@Entity
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Trainer leader;

    @NotNull
    private String city;

    @NotNull
    private PokemonType type;

    public Stadium() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || ! (o instanceof Stadium) ) return false;
        Stadium stadium = (Stadium) o;
        return Objects.equals(getLeader(), stadium.getLeader()) &&
                Objects.equals(getCity(), stadium.getCity()) &&
                Objects.equals(getType(), stadium.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLeader(), getCity(), getType());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trainer getLeader() {
        return leader;
    }

    public void setLeader(Trainer leader) {
        this.leader = leader;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }
}
