package hu.hm.fitjourneyapi.dto.fitness.excercise;

import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.model.fitness.StrengthSet;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class ExerciseStrengthSetDTO extends AbstractExerciseDTO {
    private List<StrengthSetDTO> sets;
}
