package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name="FLEXIBILITY_SETS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlexibilitySet extends Set {
    private int reps;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FlexibilitySet that = (FlexibilitySet) o;
        return reps == that.reps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reps);
    }
}
