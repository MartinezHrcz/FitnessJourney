package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.model.fitness.CardioSet;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.FlexibilitySet;
import hu.hm.fitjourneyapi.model.fitness.StrengthSet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SetMapper {
    @Mapping(target = "exercise", expression = "java(exercise)")
    StrengthSet toStrengthSet(StrengthSetDTO dto, Exercise exercise);

    @Mapping(target = "exercise", expression = "java(exercise)")
    CardioSet toCardioSet(CardioSetDTO dto, Exercise exercise);

    @Mapping(target = "exercise", expression = "java(exercise)")
    FlexibilitySet toFlexibilitySet(FlexibilitySetDTO dto, Exercise exercise);

    @Mapping(source = "exercise.id", target = "exerciseId")
    StrengthSetDTO toStrengthSetDTO(StrengthSet set);


    @Mapping(source = "exercise.id", target = "exerciseId")
    CardioSetDTO toCardioSetDTO(CardioSet set);


    @Mapping(source = "exercise.id", target = "exerciseId")
    FlexibilitySetDTO toFlexibilitySetDTO(FlexibilitySet set);

}
