package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseFlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseStrengthSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.*;

import java.util.stream.Collectors;

public class ExerciseMapper {
    //ToDO: clean this up
    public static ExerciseStrengthSetDTO toExerciseStrengthSetDTO(Exercise exercise) {
        if (exercise == null || exercise.getSets() == null) return null;
        return ExerciseStrengthSetDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .weightType(exercise.getWeightType())
                .sets(exercise.getSets().stream().map(
                        set ->
                        {
                                StrengthSet strengthSet = (StrengthSet) set;
                                return StrengthSetDTO.builder()
                                        .id(strengthSet.getId())
                                        .exerciseId(strengthSet.getExercise().getId())
                                        .reps(strengthSet.getReps())
                                        .weight(strengthSet.getWeight())
                                        .build();
                        }
                ).collect(Collectors.toList())).build();
    }

    public static ExerciseCardioSetDTO toExerciseCardioSetDTO(Exercise exercise) {
        if (exercise == null || exercise.getSets() == null) return null;
        return ExerciseCardioSetDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .weightType(exercise.getWeightType())
                .sets(exercise.getSets().stream().map(
                        set ->
                        {
                            CardioSet cardioSet = (CardioSet) set;

                            return CardioSetDTO.builder()
                                    .id(cardioSet.getId())
                                    .distanceInKilometers(cardioSet.getDistanceInKm())
                                    .durationInSeconds(cardioSet.getDurationInSeconds())
                                    .exerciseId(set.getId())
                                    .build();
                        }
                ).collect(Collectors.toList())).build();
    }

    public static ExerciseFlexibilitySetDTO toExerciseFlexibilitySetDTO(Exercise exercise) {
        if (exercise == null || exercise.getSets() == null) return null;
        return ExerciseFlexibilitySetDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .weightType(exercise.getWeightType())
                .sets(exercise.getSets().stream().map(
                        set ->
                        {
                            FlexibilitySet flexibilitySet = (FlexibilitySet) set;

                            return FlexibilitySetDTO.builder()
                                    .id(flexibilitySet.getId())
                                    .reps(flexibilitySet.getReps())
                                    .exerciseId(set.getId())
                                    .build();
                        }
                ).collect(Collectors.toList())).build();
    }


    public static Exercise toExercise(AbstractExerciseDTO dto, Workout workout) {
        if (dto == null) return null;
        return Exercise.builder()
                .type(dto.getType())
                .name(dto.getName())
                .workout(workout)
                .name(dto.getName())
                .description(dto.getDescription())
                .weightType(dto.getWeightType())
                .build();
    }



}
