package hu.hm.fitjourneyapi.dto.fitness.set;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class FlexibilitySetDTO extends AbstractSetDTO {
    private int reps;
}
