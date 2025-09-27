package hu.hm.fitjourneyapi.dto.fitness.excercise;

import hu.hm.fitjourneyapi.model.fitness.StrengthSet;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseStrengthSetDTO extends AbstractExerciseDTO {
    private List<StrengthSet> sets;
}
