package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.*;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.setExceptions.InvalidSetType;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExerciseMapper {
    @Deprecated(forRemoval = true)
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
                                        .exerciseId(exercise.getId())
                                        .reps(strengthSet.getReps())
                                        .weight(strengthSet.getWeight())
                                        .build();
                        }
                ).collect(Collectors.toList())).build();
    }

    @Deprecated(forRemoval = true)
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
                                    .exerciseId(exercise.getId())
                                    .build();
                        }
                ).collect(Collectors.toList())).build();
    }

    @Deprecated(forRemoval = true)
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
                                    .exerciseId(exercise.getId())
                                    .build();
                        }
                ).collect(Collectors.toList())).build();
    }

    //Generic mapper test
    //refactoring
    @SuppressWarnings("unchecked")
    default <T extends AbstractExerciseDTO> T toExerciseDTO(Exercise exercise) {
        if (exercise == null) return null;

        ExerciseTypes type = exercise.getType();

        switch (type) {
            case RESISTANCE, BODY_WEIGHT, NOT_GIVEN ->
            {
                return (T) ExerciseStrengthSetDTO.builder()
                        .id(exercise.getId())
                        .name(exercise.getName())
                        .description(exercise.getDescription())
                        .workoutId(exercise.getWorkout() ==  null ? null : exercise.getWorkout().getId())
                        .weightType(exercise.getWeightType())
                        .type(exercise.getType())
                        .sets(exercise.getSets().stream().map(
                                set ->
                                {
                                    StrengthSet strengthSet = (StrengthSet) set;
                                    return StrengthSetDTO.builder()
                                            .id(strengthSet.getId())
                                            .exerciseId(exercise.getId())
                                            .reps(strengthSet.getReps())
                                            .weight(strengthSet.getWeight())
                                            .build();
                                }
                        ).collect(Collectors.toList())).build();
            }

            case CARDIO ->
            {
                return (T) ExerciseCardioSetDTO.builder()
                        .id(exercise.getId())
                        .name(exercise.getName())
                        .description(exercise.getDescription())
                        .weightType(exercise.getWeightType())
                        .workoutId(exercise.getWorkout() ==  null ? null : exercise.getWorkout().getId())
                        .type(exercise.getType())
                        .sets(exercise.getSets().stream().map(
                                set ->
                                {
                                    CardioSet cardioSet = (CardioSet) set;

                                    return CardioSetDTO.builder()
                                            .id(cardioSet.getId())
                                            .distanceInKilometers(cardioSet.getDistanceInKm())
                                            .durationInSeconds(cardioSet.getDurationInSeconds())
                                            .exerciseId(exercise.getId())
                                            .build();
                                }
                        ).collect(Collectors.toList())).build();
            }

            case FLEXIBILITY -> {
                return (T) ExerciseFlexibilitySetDTO.builder()
                        .id(exercise.getId())
                        .name(exercise.getName())
                        .description(exercise.getDescription())
                        .workoutId(exercise.getWorkout() ==  null ? null : exercise.getWorkout().getId())
                        .weightType(exercise.getWeightType())
                        .type(exercise.getType())
                        .sets(exercise.getSets().stream().map(
                                set ->
                                {
                                    FlexibilitySet flexibilitySet = (FlexibilitySet) set;

                                    return FlexibilitySetDTO.builder()
                                            .id(flexibilitySet.getId())
                                            .reps(flexibilitySet.getReps())
                                            .exerciseId(exercise.getId())
                                            .build();
                                }
                        ).collect(Collectors.toList())).build();
            }
            default -> throw new  InvalidSetType(exercise.getName());
        }
    }


    @Mapping(source ="dto.id", target = "id")
    @Mapping(source="dto.name", target = "name")
    @Mapping(source = "dto.description", target = "description")
    @Mapping(target="workout", expression="java(workout)")
    Exercise toExercise(AbstractExerciseDTO dto, Workout workout);

    @Mapping(source ="dto.id", target = "id")
    @Mapping(source="dto.name", target = "name")
    @Mapping(source = "dto.description", target = "description")
    @Mapping(source="dto.type", target = "type")
    Exercise toExercise(AbstractExerciseDTO dto);

    default List<Exercise> toExercises(List<AbstractExerciseDTO> exerciseDTOS, Workout workout){
        if (exerciseDTOS == null || exerciseDTOS.isEmpty()) return null;
        return exerciseDTOS.stream().map(dto -> toExercise(dto, workout)).collect(Collectors.toList());
    }



    default List<AbstractExerciseDTO> toExerciseDTOs(List<Exercise> exercises){
        if (exercises == null || exercises.isEmpty()) return null;

        return exercises.stream()
                .map(
                        exercise ->
                        {
                            switch (exercise.getType()){
                                case ExerciseTypes.RESISTANCE, ExerciseTypes.BODY_WEIGHT, NOT_GIVEN: return toExerciseStrengthSetDTO(exercise);
                                case ExerciseTypes.CARDIO: return toExerciseCardioSetDTO(exercise);
                                case ExerciseTypes.FLEXIBILITY: return toExerciseFlexibilitySetDTO(exercise);
                                default: throw new InvalidSetType("Incorrect exercise type " + exercise.getType());
                            }
                        }
                ).collect(Collectors.toList());
    }

    default void updateExerciseFields(Exercise exercise, ExerciseUpdateDTO update) {
        exercise.setName(update.getName());
        exercise.setDescription(update.getDescription());
        exercise.setWeightType(update.getWeightType());
        exercise.setType(update.getType());
    }


}
