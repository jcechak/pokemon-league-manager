package cz.muni.fi.pa165.pokemon.dto;

import java.util.Objects;

/**
 * Data transfer object used for transferring information about badge.
 *
 * @author Marek Sabo
 */
public class BadgeDTO {

    private Long id;

    private Long stadiumId;

    private Long trainerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BadgeDTO badgeDTO = (BadgeDTO) o;
        return Objects.equals(getStadiumId(), badgeDTO.getStadiumId()) &&
                Objects.equals(getTrainerId(), badgeDTO.getTrainerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStadiumId(), getTrainerId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(Long stadiumId) {
        this.stadiumId = stadiumId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }
}
