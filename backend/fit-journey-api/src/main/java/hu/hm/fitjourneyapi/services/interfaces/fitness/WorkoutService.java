package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutUpdateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface WorkoutService {
    WorkoutDTO createWorkout(WorkoutCreateDTO workoutCreateDTO);

    WorkoutDTO getWorkoutByWorkoutId(long id);

    List<WorkoutDTO> getWorkouts();

    List<WorkoutDTO> getWorkoutByUserId(UUID id);

    WorkoutDTO updateWorkout(long id, WorkoutUpdateDTO workoutUpdateDTO);

    WorkoutDTO addExerciseToWorkout(long workoutId, long exerciseId);

    void deleteWorkoutById(long id);
}
