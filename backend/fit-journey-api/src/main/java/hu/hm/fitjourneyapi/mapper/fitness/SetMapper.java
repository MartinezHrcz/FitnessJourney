package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.model.fitness.CardioSet;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.FlexibilitySet;
import hu.hm.fitjourneyapi.model.fitness.StrengthSet;

public class SetMapper {
    public StrengthSet toStrengthSet(StrengthSetDTO dto, Exercise exercise) {
        return StrengthSet.builder()
                .exercise(exercise)
                .reps(dto.getReps())
                .weight(dto.getWeight())
                .build();
    }
    public CardioSet toCardioSet(CardioSetDTO dto, Exercise exercise) {
        return CardioSet.builder()
                .exercise(exercise)
                .durationInSeconds(dto.getDurationInSeconds())
                .distanceInKm(dto.getDistanceInKilometers())
                .build();
    }
    public FlexibilitySet toFlexibilitySet(FlexibilitySetDTO dto, Exercise exercise) {
        return FlexibilitySet.builder()
                .exercise(exercise)
                .reps(dto.getReps())
                .build();
    }

    public StrengthSetDTO toStrengthSetDTO(StrengthSet set) {
        return StrengthSetDTO
                .builder()
                .id(set.getId())
                .exerciseId(set.getExercise().getId())
                .reps(set.getReps())
                .weight(set.getWeight())
                .build();
    }

    public CardioSetDTO toCardioSetDTO(CardioSet set) {
        return CardioSetDTO
                .builder()
                .id(set.getId())
                .exerciseId(set.getId())
                .distanceInKilometers(set.getDistanceInKm())
                .durationInSeconds(set.getDurationInSeconds())
                .build();
    }

    public FlexibilitySetDTO toFlexibilitySetDTO(FlexibilitySet set) {
        return FlexibilitySetDTO
                .builder()
                .id(set.getId())
                .reps(set.getReps())
                .exerciseId(set.getExercise().getId())
                .build();
    }

}
