package hu.hm.fitjourneyapi.dto.fitness.excercise;

import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.model.fitness.CardioSet;
import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
public class ExerciseCardioSetDTO extends AbstractExerciseDTO {
    private List<CardioSetDTO> sets;

}
