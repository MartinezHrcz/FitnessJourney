package hu.hm.fitjourneyapi.dto.fitness.set;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class StrengthSetDTO extends AbstractSetDTO{
    private int reps;
    private int weight;
}
