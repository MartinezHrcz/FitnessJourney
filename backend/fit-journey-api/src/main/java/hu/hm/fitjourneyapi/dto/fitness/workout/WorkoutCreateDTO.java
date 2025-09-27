package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutCreateDTO  extends AbstractWorkoutDTO {
    private long userId;
}
