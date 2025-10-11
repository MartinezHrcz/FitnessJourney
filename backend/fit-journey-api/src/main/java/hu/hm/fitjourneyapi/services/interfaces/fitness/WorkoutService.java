package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;

import java.util.List;

public interface WorkoutService {
    WorkoutDTO createWorkout(WorkoutCreateDTO workoutCreateDTO);

    WorkoutDTO getWorkoutByWorkoutId(long id);

    List<WorkoutDTO> getWorkouts();

    List<WorkoutDTO> getWorkoutByUserId(long id);

    WorkoutDTO updateWorkout(long id,WorkoutDTO workoutDTO);

    void deleteWorkoutById(long id);
}
