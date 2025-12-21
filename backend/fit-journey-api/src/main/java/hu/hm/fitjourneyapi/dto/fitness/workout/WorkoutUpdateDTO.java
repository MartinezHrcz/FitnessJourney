package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class WorkoutUpdateDTO extends AbstractWorkoutDTO {
    private int lengthInMins;
}
