package cz.muni.fi.pa165.pokemon.entity;

import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

package cz.muni.fi.pa165.pokemon.entity;

/**
 *
 * @Dominika Talianova
 */
public class Badge {
    
    private Long id;
    private Stadium stadium;
    private Trainer trainer;
    
    public Badge()
    {
      
    }
    
    public Long getId() 
    {
      return id;
    }
        
    public void setId(Long id)
    {
      this.id = id;  
    }
    
    public Stadium getStadium()
    {
        return stadium;
    }
    
    public void setStadium(Stadium stadium)
    {
        this.stadium = stadium;
    }
    
    public Trainer getTrainer()
    {
        return trainer;
    }
    
    public void setTrainer(Trainer trainer)
    {
        this.trainer = trainer;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if(this==other)
        {
            return true;
        }
        if(!(other instanceof Badge))
        {
            return false;
        }
        
        otherType = Badge(other);
        
        return this.stadium.equals(otherType.stadium) && this.trainer.equals(otherType.trainer);
    }
    
    @Override
    public int hashCode()
    {
        if(id==null)
        {
            Long tempId = new Long (-1);
            return tempId.hashCode();
            
        }
        return id.hashCode();
    }
   
    
    
}
