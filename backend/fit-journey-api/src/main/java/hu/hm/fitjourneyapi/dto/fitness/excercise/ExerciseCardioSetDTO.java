package hu.hm.fitjourneyapi.dto.fitness.excercise;

import hu.hm.fitjourneyapi.model.fitness.CardioSet;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseCardioSetDTO extends AbstractExerciseDTO {
    private List<CardioSet> sets;

}
