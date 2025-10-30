package hu.hm.fitjourneyapi.dto.user.fitness;


import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.user.AbstractUserDTO;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor
public class UserWithWorkoutsDTO extends AbstractUserDTO {
    private UUID id;
    private List<WorkoutDTO> workouts;
}
