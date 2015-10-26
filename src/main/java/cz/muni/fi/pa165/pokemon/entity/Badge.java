package cz.muni.fi.pa165.pokemon.entity;

import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 *
 * @author 
 */
@Entity
public class Badge {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Stadium stadium;
    
    @ManyToOne
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
        
        Badge otherType = (Badge) other;
        
        return this.stadium.equals(otherType.getStadium()) && this.trainer.equals(otherType.getTrainer());
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.stadium, this.trainer);
    }

    @Override
    public String toString() {
        return "Badge{" +
                "id=" + id +
                ", stadium=" + stadium +
                ", trainer=" + trainer +
                '}';
    }

}
