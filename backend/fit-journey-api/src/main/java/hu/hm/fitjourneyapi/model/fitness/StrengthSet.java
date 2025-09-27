package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "STRENGTH_SETS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StrengthSet extends Set{
    private int reps;
    private int weight;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StrengthSet that = (StrengthSet) o;
        return reps == that.reps && weight == that.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reps, weight);
    }
}
