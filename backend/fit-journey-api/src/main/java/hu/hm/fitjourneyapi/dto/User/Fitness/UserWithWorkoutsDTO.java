package hu.hm.fitjourneyapi.dto.User.Fitness;


import hu.hm.fitjourneyapi.dto.User.AbstractUserDTO;
import hu.hm.fitjourneyapi.model.fitness.Workout;

import java.util.List;

public class UserWithWorkoutsDTO extends AbstractUserDTO {
    private long id;
    private List<Workout> workouts;
}
