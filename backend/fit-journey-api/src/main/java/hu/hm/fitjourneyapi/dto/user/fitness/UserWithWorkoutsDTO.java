package hu.hm.fitjourneyapi.dto.user.fitness;


import hu.hm.fitjourneyapi.dto.user.AbstractUserDTO;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class UserWithWorkoutsDTO extends AbstractUserDTO {
    private long id;
    private List<Workout> workouts;
}
