package cz.muni.fi.pa165.pokemon.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents Tournament Data Transfer Object
 * 
 * @author Milos Bartak
 */
public class TournamentDTO {
    private Long id;

    /**
     * Name of the tournament
     */
    private String tournamentName;

    /**
     * Id of the stadium the tournament is played at
     */
    private Long stadiumId;

    /**
     * Number representing how many pokemons each trainer must have
     */
    private int minimalPokemonCount;
    
    /**
     * Number representing minimal level that all pokemons must have
     */
    private int minimalPokemonLevel;
    
    /**
     * List of badges all trainers must have to participate
     */
    private List<BadgeDTO> badges = new ArrayList<>();
    
    /**
     * The list of participants
     */

    private final List<TrainerDTO> trainers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Long getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(Long stadiumId) {
        this.stadiumId = stadiumId;
    }

    public int getMinimalPokemonCount() {
        return minimalPokemonCount;
    }

    public void setMinimalPokemonCount(int minimalPokemonCount) {
        this.minimalPokemonCount = minimalPokemonCount;
    }

    public int getMinimalPokemonLevel() {
        return minimalPokemonLevel;
    }

    public void setMinimalPokemonLevel(int minimalPokemonLevel) {
        this.minimalPokemonLevel = minimalPokemonLevel;
    }

    public List<BadgeDTO> getBadges() {
        return badges;
    }

    public void setBadges(List<BadgeDTO> badges) {
        this.badges = badges;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.tournamentName);
        hash = 47 * hash + Objects.hashCode(this.stadiumId);
        hash = 47 * hash + this.minimalPokemonCount;
        hash = 47 * hash + this.minimalPokemonLevel;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TournamentDTO other = (TournamentDTO) obj;
        if (!Objects.equals(this.tournamentName, other.tournamentName)) {
            return false;
        }
        if (!Objects.equals(this.stadiumId, other.stadiumId)) {
            return false;
        }
        if (this.minimalPokemonCount != other.minimalPokemonCount) {
            return false;
        }
        if (this.minimalPokemonLevel != other.minimalPokemonLevel) {
            return false;
        }
        return true;
    }
    
    
}
