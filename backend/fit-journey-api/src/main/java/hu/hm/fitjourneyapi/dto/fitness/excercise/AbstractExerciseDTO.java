package hu.hm.fitjourneyapi.dto.fitness.excercise;

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
}
