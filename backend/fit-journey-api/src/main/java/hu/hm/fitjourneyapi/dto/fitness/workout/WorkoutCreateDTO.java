package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutCreateDTO  extends AbstractWorkoutDTO {
    private long userId;
}
