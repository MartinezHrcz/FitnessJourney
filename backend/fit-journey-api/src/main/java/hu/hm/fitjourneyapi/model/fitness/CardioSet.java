package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "CARDIO_SETS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardioSet extends Set {
    private int durationInSeconds;
    private double distanceInKm;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CardioSet cardioSet = (CardioSet) o;
        return durationInSeconds == cardioSet.durationInSeconds && Double.compare(distanceInKm, cardioSet.distanceInKm) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), durationInSeconds, distanceInKm);
    }
}
