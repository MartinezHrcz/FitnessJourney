package hu.hm.fitjourneyapi.dto.fitness.excercise;

import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractExerciseDTO {
    private long id;
    private String name;
    private String description;
    private long workoutId;
    private WeightType weightType;
    private ExerciseTypes type;
}
