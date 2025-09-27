package hu.hm.fitjourneyapi.dto.fitness.excercise;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public abstract class AbstractExerciseDTO {
    private long id;
    private String name;
    private String description;
    private long workoutId;
}
