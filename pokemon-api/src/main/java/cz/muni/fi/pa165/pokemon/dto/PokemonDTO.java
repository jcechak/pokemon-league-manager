package cz.muni.fi.pa165.pokemon.dto;

import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.Objects;

/**
 * Data transfer object for transafering information about a pokemon.
 *
 * @author Jaroslav Cechak
 */
public class PokemonDTO {

    /**
     * Id of corresponding object in persistance
     */
    private Long id;

    /**
     * Name of a pokemon (e.g. Pikachu)
     */
    private String name;

    /**
     * Nickname of a pokemon (e.g. Karel)
     */
    private String nickname;

    /**
     * Type of a pokemon, this determines his abilities and effectiveness
     * against other pokemons.
     */
    private PokemonType type;

    /**
     * The level of pokemon's skills, this determines his strength.
     */
    private int skillLevel;
    
    /**
     * Trainer (owner) of a pokemon
     */
    private TrainerDTO trainer;

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

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.nickname);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PokemonDTO)) {
            return false;
        }
        final PokemonDTO other = (PokemonDTO) obj;
        if (!Objects.equals(this.name, other.getName())) {
            return false;
        }
        if (!Objects.equals(this.nickname, other.getNickname())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pokemon{"
                + "id=" + id
                + ", name=" + name
                + ", nickname=" + nickname
                + ", type=" + type
                + ", skillLevel=" + skillLevel
                + ", trainer=" + trainer
                + '}';
    }

}
