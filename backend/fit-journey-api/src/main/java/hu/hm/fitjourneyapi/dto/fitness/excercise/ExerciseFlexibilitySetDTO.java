package hu.hm.fitjourneyapi.dto.fitness.excercise;

import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.model.fitness.FlexibilitySet;
import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
public class ExerciseFlexibilitySetDTO extends AbstractExerciseDTO {
    private List<FlexibilitySetDTO> sets;
}
