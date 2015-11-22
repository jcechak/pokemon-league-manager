package cz.muni.fi.pa165.pokemon.dto;

import cz.muni.fi.pa165.pokemon.enums.PokemonType;

import java.util.Objects;

/**
 *
 * @author Dominika Talianova
 */
public class StadiumDTO {

    private Long id;

    private String name;

    private String city;

    private TrainerDTO stadiumLeader;

    private BadgeDTO badge;

    private PokemonType stadiumType;


    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public TrainerDTO getStadiumLeader(){
        return stadiumLeader;
    }

    public void setStadiumLeader(TrainerDTO stadiumLeader){
        this.stadiumLeader = stadiumLeader;
    }

    public  BadgeDTO getBadge(){
        return badge;
    }

    public void setBadge(BadgeDTO badge){
        this.badge = badge;
    }

    public PokemonType getStadiumType(){
        return stadiumType;
    }

    public void setStadiumType(PokemonType stadiumType){
        this.stadiumType = stadiumType;
    }

    @Override
    public int hashCode(){
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.city);
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if(!(obj instanceof StadiumDTO)){
            return false;
        }
        StadiumDTO other = (StadiumDTO) obj;
        return Objects.equals(this.getName(), other.getName()) &&
               Objects.equals(this.getCity(), other.getCity()) &&
               Objects.equals(this.getBadge(), other.getBadge()) &&
               Objects.equals(this.getStadiumLeader(), other.getStadiumLeader());

    }

    @Override
    public String toString(){
        return "Stadium{ "
                + "id = " + id
                + ", name = " + name
                + ", city = " + city
                + ", stadium leader = " + stadiumLeader
                + ", stadium type = " + stadiumType
                + ", badge = " + badge
                + "}";
    }
    
}
