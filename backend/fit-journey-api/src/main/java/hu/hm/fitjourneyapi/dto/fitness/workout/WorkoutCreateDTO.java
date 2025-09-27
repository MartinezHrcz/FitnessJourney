package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class WorkoutCreateDTO  extends AbstractWorkoutDTO {
    private long userId;
}
