package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ExerciseMapper.class)
public interface WorkoutMapper {
    @Mapping(source = "user", target = "userId", qualifiedByName = "userToId")
    WorkoutDTO toDTO(Workout workout);

    List<WorkoutDTO> toDTOList(List<Workout> workouts);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "dto.name", target = "name")
    @Mapping(source = "dto.description", target = "description")
    @Mapping(target = "user", expression = "java(user)")
    Workout toWorkout(WorkoutCreateDTO dto, User user);

    @Named("userToId")
    static UUID userToId(User user) {
        return user != null ? user.getId() : null;
    }

}
