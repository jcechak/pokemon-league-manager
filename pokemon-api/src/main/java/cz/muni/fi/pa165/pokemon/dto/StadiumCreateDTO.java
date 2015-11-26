package cz.muni.fi.pa165.pokemon.dto;


import cz.muni.fi.pa165.pokemon.enums.PokemonType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Data transfer object for holding data from form or something similar.
 * @author Dominika Talianova
 */
public class StadiumCreateDTO {

    @NotNull
    @Size(min = 2, max = 50)
    private String city;

    @NotNull
    private Long stadiumLeaderId;

    @NotNull
    private PokemonType type;


    public void setCity(String city){
        this.city=city;
    }

    public String getCity(){
        return city;
    }

    public void setStadiumLeaderId(Long stadiumLeaderId){

        this.stadiumLeaderId = stadiumLeaderId;
    }

    public Long getStadiumLeaderId(){
        return stadiumLeaderId;
    }

    public void setType(PokemonType type){
        this.type= type;
    }

    public PokemonType getType(){
        return type;
    }

    @Override
    public int hashCode(){
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.city);
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if(!(obj instanceof StadiumCreateDTO)){
            return false;
        }
        StadiumCreateDTO other = (StadiumCreateDTO) obj;
        return Objects.equals(this.getCity(), other.getCity()) &&
                Objects.equals(this.getType(), other.getType()) &&
                Objects.equals(this.getStadiumLeaderId(), other.getStadiumLeaderId());

    }

    @Override
    public String toString(){
        return "Stadium{ "
                + "stadium's leader id = " + stadiumLeaderId
                + ", city = " + city
                + ", stadium type = " + type
                + "}";
    }

}
