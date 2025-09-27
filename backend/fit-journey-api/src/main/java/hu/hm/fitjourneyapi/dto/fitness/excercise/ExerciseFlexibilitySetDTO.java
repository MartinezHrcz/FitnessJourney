package hu.hm.fitjourneyapi.dto.fitness.excercise;

import hu.hm.fitjourneyapi.model.fitness.FlexibilitySet;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseFlexibilitySetDTO extends AbstractExerciseDTO {
    private List<FlexibilitySet> sets;
}
