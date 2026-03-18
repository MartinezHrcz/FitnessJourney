package hu.hm.fitjourneyapi.mapper.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workoutPlan.PlanExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.workoutPlan.WorkoutPlanDTO;
import hu.hm.fitjourneyapi.model.fitness.PlanExercise;
import hu.hm.fitjourneyapi.model.fitness.WorkoutPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkoutPlanMapper {

    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "exercises", source = "exercises")
    WorkoutPlanDTO toDTO(WorkoutPlan workoutPlan);

    List<WorkoutPlanDTO> toDTOList(List<WorkoutPlan> plans);

    @Mapping(target = "id", ignore = true)
    PlanExerciseDTO toPlanExerciseDTO(PlanExercise planExercise);
}
