package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;

public class WorkoutMapper {
    public static WorkoutDTO toDTO(Workout workout) {
        if(workout == null) return null;
        return WorkoutDTO.builder()
                .name(workout.getName())
                .description(workout.getDescription())
                .exercises(workout.getExercises())
                .lengthInMins(workout.getLengthInMins())
                .userId(workout.getUser().getId())
                .build();
    }
    public static Workout toWorkout(WorkoutCreateDTO dto, User user) {
        if(dto == null) return null;
        return Workout.builder()
                .user(user)
                .name(dto.getName())
                .description(dto.getDescription()).build();

    }
}
