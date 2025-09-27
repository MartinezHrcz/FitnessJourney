package hu.hm.fitjourneyapi.dto.fitness.excercise;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public abstract class AbstractExerciseDTO {
    private long id;
    private String name;
    private String description;
    private long workoutId;
}
