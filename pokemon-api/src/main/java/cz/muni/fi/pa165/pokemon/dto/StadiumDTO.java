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

    private Long stadiumLeaderId;

    private PokemonType type;


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

    public PokemonType getType(){
        return type;
    }

    public void setType(PokemonType type){
        this.type = type;
    }

    public Long getStadiumLeaderId(){
        return stadiumLeaderId;
    }

    public void setStadiumLeaderId(Long id){
        this.stadiumLeaderId = id;
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
        if(!(obj instanceof StadiumDTO)){
            return false;
        }
        StadiumDTO other = (StadiumDTO) obj;
        return Objects.equals(this.getCity(), other.getCity()) &&
                Objects.equals(this.getType(), other.getType());

    }

    @Override
    public String toString(){
        return "Stadium{ "
                + "id = " + id
                + ", city = " + city
                + ", stadium's leader id = " + stadiumLeaderId
                + ", stadium type = " + type
                + "}";
    }

}
