package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class WorkoutCreateDTO  extends AbstractWorkoutDTO {
    private long userId;
}
