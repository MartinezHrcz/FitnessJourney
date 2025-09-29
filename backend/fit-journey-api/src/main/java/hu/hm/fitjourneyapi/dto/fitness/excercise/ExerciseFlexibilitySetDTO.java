package hu.hm.fitjourneyapi.dto.fitness.excercise;

import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@NoArgsConstructor
public class ExerciseFlexibilitySetDTO extends AbstractExerciseDTO {
    private List<FlexibilitySetDTO> sets;
}
