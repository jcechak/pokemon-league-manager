package cz.muni.fi.pa165.pokemon.dto;

import cz.muni.fi.pa165.pokemon.enums.PokemonType;

import java.util.Objects;

/**
 * Data transfer object for holding data from form or something similar.
 * @author Dominika Talianova
 */
public class StadiumCreateDTO {

    private String city;

    private Long stadiumId;

    private TrainerDTO stadiumLeader;

    private PokemonType stadiumType;


    public void setCity(String city){
        this.city=city;
    }

    public String getCity(){
        return city;
    }

    public void setStadiumId(Long id){
        this.stadiumId = id;
    }

    public Long getStadiumId(){
        return stadiumId;
    }

    public void setStadiumLeader(TrainerDTO stadiumLeader){

        this.stadiumLeader = stadiumLeader;
    }

    public TrainerDTO getStadiumLeader(){
        return stadiumLeader;
    }

    public void setStadiumType(PokemonType type){
        this.stadiumType= type;
    }

    public PokemonType getStadiumType(){
        return stadiumType;
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
                + "stadium's id = " + stadiumId
                + ", city = " + city
                + ", stadium leader = " + stadiumLeader
                + ", stadium type = " + stadiumType
                + "}";
    }

}
