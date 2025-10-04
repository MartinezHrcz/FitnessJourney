package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;

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
                .name("Test Workout")
                .description("This is for a test")
                .lengthInMins(60)
                .userId(userId)
                .build();
        return dto;
    }

    public static Workout getWorkout(User user) {
        Workout workout = Workout.builder()
                .user(user)
                .name("Test Workout")
                .description("This is for a test")
                .lengthInMins(60)
                .build();
        return workout;
    }

}
