package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseCardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseFlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseStrengthSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.model.fitness.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExerciseMapper {

    default ExerciseStrengthSetDTO toExerciseStrengthSetDTO(Exercise exercise) {
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

    default ExerciseCardioSetDTO toExerciseCardioSetDTO(Exercise exercise) {
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

    default ExerciseFlexibilitySetDTO toExerciseFlexibilitySetDTO(Exercise exercise) {
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


    @Mapping(target="workout", expression="java(workout)")
    Exercise toExercise(AbstractExerciseDTO dto, Workout workout);



}
