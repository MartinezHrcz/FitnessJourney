package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseStrengthSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.model.fitness.CardioSet;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.StrengthSet;
import hu.hm.fitjourneyapi.model.fitness.Workout;

import java.util.stream.Collectors;

public class ExerciseMapper {
    //ToDO: clean this up
    public static ExerciseStrengthSetDTO toExerciseStrengthSetDTO(Exercise exercise) {
        if (exercise == null && exercise.getSets() == null) return null;
        return ExerciseStrengthSetDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
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

    public static ExerciseCardioSetDTO exerciseCardioSetDTO(Exercise exercise) {
        if (exercise == null && exercise.getSets() == null) return null;
        return ExerciseCardioSetDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
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



}
