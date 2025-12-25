package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface WorkoutService {
    UUID createWorkout(WorkoutCreateDTO workoutCreateDTO);

    WorkoutDTO getWorkoutByWorkoutId(UUID id);

    List<WorkoutDTO> getWorkouts();

    List<WorkoutDTO> getWorkoutByUserId(UUID id);

    WorkoutDTO updateWorkout(UUID id, WorkoutUpdateDTO workoutUpdateDTO);

    WorkoutDTO addExerciseToWorkout(UUID workoutId, UUID exerciseId);

    WorkoutDTO addDefaultExerciseToWorkout(UUID workoutId, UUID templateId);

    WorkoutDTO addUserExerciseToWorkout(UUID workoutId, UUID templateId);

    WorkoutDTO removeExerciseFromWorkout(UUID workoutId, UUID exerciseId);

    WorkoutDTO finishWorkout(UUID workoutId);

    WorkoutDTO cancelWorkout(UUID workoutId);

    void deleteWorkoutById(UUID id);
}
