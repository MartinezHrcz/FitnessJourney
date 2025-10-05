package hu.hm.fitjourneyapi.dto.user.fitness;


import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.user.AbstractUserDTO;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
public class UserWithWorkoutsDTO extends AbstractUserDTO {
    private long id;
    private List<WorkoutDTO> workouts;
}
