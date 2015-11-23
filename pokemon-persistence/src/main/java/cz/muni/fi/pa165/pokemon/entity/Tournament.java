package cz.muni.fi.pa165.pokemon.entity;

import java.util.List;
import java.util.Objects;


/**
 * Class representing a tournament in which trainers may participate
 * 
 * @author Milos Bartak
 */
public class Tournament {
    
    private Long id;

    private String tournamentName;

    private String townName;
    
    private int minimalPokemonCount;
    
    private int minimalPokemonLevel;
    
    private List<Badge> badges;

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
    
    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
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

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.tournamentName);
        hash = 43 * hash + Objects.hashCode(this.townName);
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
        final Tournament other = (Tournament) obj;
        if (!Objects.equals(this.tournamentName, other.tournamentName)) {
            return false;
        }
        if (!Objects.equals(this.townName, other.townName)) {
            return false;
        }
        return true;
    }
    
    
}
