package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.premadeExercises.DefaultExerciseDTO;
import hu.hm.fitjourneyapi.model.fitness.DefaultExercise;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DefaultExerciseMapper {

    DefaultExercise toEntity(DefaultExerciseDTO dto);

    DefaultExerciseDTO toDto(DefaultExercise entity);

    List<DefaultExerciseDTO> toDto(List<DefaultExercise> entity);

    List<DefaultExercise> toEntity(List<DefaultExerciseDTO> dto);
}
