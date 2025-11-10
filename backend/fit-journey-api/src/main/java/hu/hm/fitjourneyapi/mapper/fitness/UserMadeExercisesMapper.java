package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserExerciseUpdateDto;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserMadeExercisesDTO;
import hu.hm.fitjourneyapi.model.fitness.UserMadeTemplates;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMadeExercisesMapper {

    UserMadeTemplates toEntity(UserMadeExercisesDTO dto);

    UserMadeExercisesDTO toDto(UserMadeTemplates entity);

    List<UserMadeExercisesDTO> toDto(List<UserMadeTemplates> entity);

    List<UserMadeTemplates> toEntity(List<UserMadeExercisesDTO> dto);

    UserMadeTemplates toEntity(UserExerciseUpdateDto dto);
}
