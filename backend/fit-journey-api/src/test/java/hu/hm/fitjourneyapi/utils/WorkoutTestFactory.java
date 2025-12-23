package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkoutTestFactory {

    public static WorkoutCreateDTO getWorkoutCreateDTO(UUID userId) {
        WorkoutCreateDTO dto = WorkoutCreateDTO.builder()
                .name("Test Workout")
                .description("This is for a test")
                .userId(userId)
                .build();
        return dto;
    }

    public static WorkoutDTO getWorkoutDTO(UUID userId) {
        WorkoutDTO dto = WorkoutDTO.builder()
                .id(UUID.randomUUID())
                .name("Test Workout")
                .description("This is for a test")
                .exercises(new ArrayList<>())
                .userId(userId)
                .build();
        AbstractExerciseDTO exerciseDTO = ExerciseTestFactory.getExerciseDTO(ExerciseTypes.RESISTANCE, dto.getId());
        dto.getExercises().add(exerciseDTO);
        return dto;
    }

    public static Workout getWorkout(User user) {
        Workout workout = Workout.builder()
                .user(user)
                .name("Test Workout")
                .description("This is for a test")
                .exercises(new ArrayList<>())
                .build();
        List<Exercise> exercises = List.of(ExerciseTestFactory.getExercise(workout));
        workout.setExercises(exercises);
        return workout;
    }

}
