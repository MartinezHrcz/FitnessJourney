package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.fitness.set.AbstractSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.*;

import java.util.UUID;

public class SetTestFactory {
    public static Set getSet(Exercise exercise) {

        switch (exercise.getType())
        {
            case NOT_GIVEN, RESISTANCE, BODY_WEIGHT -> {
                return
                        StrengthSet.builder()
                                .exercise(exercise)
                                .reps(10)
                                .weight(100)
                                .build();
            }
            case FLEXIBILITY -> {
                return
                        FlexibilitySet.builder()
                                .exercise(exercise)
                                .reps(10)
                                .build();
            }
            case CARDIO -> {
                return
                        CardioSet.builder()
                                .distanceInKm(1)
                                .durationInSeconds(100)
                                .build();
            }

        }
        return null;
    }

    public static AbstractSetDTO getSetDTO(UUID exerciseId, ExerciseTypes exerciseType) {
        switch (exerciseType)
        {
            case NOT_GIVEN, RESISTANCE, BODY_WEIGHT -> {
                return
                        StrengthSetDTO
                                .builder()
                                .exerciseId(exerciseId)
                                .reps(10)
                                .weight(100)
                        .build();
            }
            case FLEXIBILITY -> {
                return
                        FlexibilitySetDTO.builder()
                                .exerciseId(exerciseId)
                                .reps(10)
                        .build();
            }
            case CARDIO -> {
                return
                        CardioSetDTO.builder()
                                .exerciseId(exerciseId)
                                .durationInSeconds(100)
                                .distanceInKilometers(1)
                        .build();
            }
        }
        return null;
    }
}
