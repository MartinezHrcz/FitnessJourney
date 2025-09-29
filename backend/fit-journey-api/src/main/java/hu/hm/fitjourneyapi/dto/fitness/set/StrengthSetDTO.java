package hu.hm.fitjourneyapi.dto.fitness.set;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class StrengthSetDTO extends AbstractSetDTO{
    private int reps;
    private int weight;
}
