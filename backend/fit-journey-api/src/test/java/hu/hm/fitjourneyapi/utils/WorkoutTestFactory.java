package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Workout;

import java.util.List;

public class WorkoutTestFactory {

    public static WorkoutCreateDTO getWorkoutCreateDTO(long userId) {
        WorkoutCreateDTO dto = WorkoutCreateDTO.builder()
                .name("Test Workout")
                .description("This is for a test")
                .userId(userId)
                .build();
        return dto;
    }

    public static WorkoutDTO getWorkoutDTO(long userId) {
        WorkoutDTO dto = WorkoutDTO.builder()
                .id(1L)
                .name("Test Workout")
                .description("This is for a test")
                .lengthInMins(60)
                .userId(userId)
                .build();
        AbstractExerciseDTO exerciseDTO = ExerciseTestFactory.getExerciseDTO(ExerciseTypes.RESISTANCE, dto.getId());
        return dto;
    }

    public static Workout getWorkout(User user) {
        Workout workout = Workout.builder()
                .user(user)
                .name("Test Workout")
                .description("This is for a test")
                .lengthInMins(60)
                .exercises(List.of())
                .build();
        List<Exercise> exercises = List.of(ExerciseTestFactory.getExercise(workout));
        workout.setExercises(exercises);
        return workout;
    }

}
