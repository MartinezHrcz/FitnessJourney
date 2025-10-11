package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseFlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseStrengthSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Set;
import hu.hm.fitjourneyapi.model.fitness.Workout;

import java.util.List;

public class ExerciseTestFactory {

    public static Exercise getExercise(Workout workout) {
        Exercise exercise = Exercise
                .builder()
                .id(1L)
                .name("Test Exercise")
                .description("Exercise desc")
                .workout(workout)
                .type(ExerciseTypes.RESISTANCE)
                .build();

        List<Set> sets =List.of(SetTestFactory.getSet(exercise));
        exercise.setSets(sets);
        return exercise;
    }

    public static AbstractExerciseDTO getExerciseDTO(ExerciseTypes exerciseType, long workoutId) {
        switch (exerciseType)
        {
            case RESISTANCE, NOT_GIVEN, BODYWEIGHT:
            {
                ExerciseStrengthSetDTO es = ExerciseStrengthSetDTO.builder()
                        .id(1L)
                        .name("Test Exercise")
                        .description("Exercise desc")
                        .workoutId(workoutId)
                        .type(ExerciseTypes.RESISTANCE)
                    .build();
                es.getSets().add((StrengthSetDTO) SetTestFactory.getSetDTO(es.getId(), exerciseType));
                return es;
            }
            case CARDIO:
            {
                 ExerciseCardioSetDTO ec= ExerciseCardioSetDTO
                        .builder()
                         .id(1L)
                        .name("Test Exercise")
                        .description("Test exercise")
                        .workoutId(workoutId)
                        .type(ExerciseTypes.CARDIO)
                        .build();
                 ec.getSets().add((CardioSetDTO) SetTestFactory.getSetDTO(ec.getId(), exerciseType));
            }
            case FLEXIBILITY:
            {
                ExerciseFlexibilitySetDTO ef = ExerciseFlexibilitySetDTO
                        .builder()
                        .id(1L)
                        .name("Test Exercise")
                        .description("Test exercise")
                        .workoutId(workoutId)
                        .type(ExerciseTypes.FLEXIBILITY)
                    .build();
                ef.getSets().add((FlexibilitySetDTO) SetTestFactory.getSetDTO(ef.getId(), exerciseType));
                return ef;
            }
        }
        return null;
    }
}
