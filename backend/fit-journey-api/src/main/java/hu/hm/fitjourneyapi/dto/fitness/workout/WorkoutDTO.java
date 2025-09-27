package hu.hm.fitjourneyapi.dto.fitness.workout;

import hu.hm.fitjourneyapi.model.fitness.Exercise;

import java.util.List;

public class WorkoutDTO extends AbstractWorkoutDTO {
    private long id;
    private int lengthInMins;
    private List<Exercise> exercises;
}
