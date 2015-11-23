package cz.muni.fi.pa165.pokemon.dto;

import cz.muni.fi.pa165.pokemon.enums.PokemonType;

import java.util.Objects;

/**
 * Implementation of data transfer object, used for transfer of stadium data.
 * @author Dominika Talianova
 */
public class StadiumDTO {

    private Long id;

    private String city;

    private TrainerDTO stadiumLeader;

    private PokemonType stadiumType;


    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
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

    public PokemonType getStadiumType(){
        return stadiumType;
    }

    public void setStadiumType(PokemonType stadiumType){
        this.stadiumType = stadiumType;
    }

    @Override
    public int hashCode(){
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.stadiumLeader);
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
        return Objects.equals(this.getCity(), other.getCity()) &&
               Objects.equals(this.getStadiumType(), other.getStadiumType()) &&
               Objects.equals(this.getStadiumLeader(), other.getStadiumLeader());

    }

    @Override
    public String toString(){
        return "Stadium{ "
                + "id = " + id
                + ", city = " + city
                + ", stadium leader = " + stadiumLeader
                + ", stadium type = " + stadiumType
                + "}";
    }
    
}
