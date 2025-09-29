package hu.hm.fitjourneyapi.dto.fitness.set;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class FlexibilitySetDTO extends AbstractSetDTO {
    private int reps;
}
