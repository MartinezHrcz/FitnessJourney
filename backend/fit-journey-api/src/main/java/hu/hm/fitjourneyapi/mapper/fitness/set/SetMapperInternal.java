package hu.hm.fitjourneyapi.mapper.fitness.set;

import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.model.fitness.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SetMapperInternal {

    @Mapping(source="dto.id", target = "id")
    @Mapping(target = "exercise", expression = "java(exercise)")
    StrengthSet toStrengthSet(StrengthSetDTO dto, Exercise exercise);

    @Mapping(source="dto.id", target = "id")
    @Mapping(target = "exercise", expression = "java(exercise)")
    CardioSet toCardioSet(CardioSetDTO dto, Exercise exercise);

    @Mapping(source="dto.id", target = "id")
    @Mapping(target = "exercise", expression = "java(exercise)")
    FlexibilitySet toFlexibilitySet(FlexibilitySetDTO dto, Exercise exercise);

    @Mapping(source="set.id", target = "id")
    @Mapping(source = "exercise.id", target = "exerciseId")
    StrengthSetDTO toStrengthSetDTO(StrengthSet set);

    @Mapping(source="set.id", target = "id")
    @Mapping(source = "exercise.id", target = "exerciseId")
    CardioSetDTO toCardioSetDTO(CardioSet set);

    @Mapping(source="set.id", target = "id")
    @Mapping(source = "exercise.id", target = "exerciseId")
    FlexibilitySetDTO toFlexibilitySetDTO(FlexibilitySet set);

    List<StrengthSetDTO> toStrengthSetDTOList(List<StrengthSet> sets);
    List<CardioSetDTO> toCardioSetDTOList(List<CardioSet> sets);
    List<FlexibilitySetDTO> toFlexibilitySetDTOList(List<FlexibilitySet> sets);
}
