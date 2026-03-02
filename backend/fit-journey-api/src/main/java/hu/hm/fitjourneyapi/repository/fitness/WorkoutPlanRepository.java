package hu.hm.fitjourneyapi.repository.fitness;

import hu.hm.fitjourneyapi.model.fitness.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, UUID> {

    List<WorkoutPlan> findWorkoutPlansByNameContainingIgnoreCase(String name);

    List<WorkoutPlan> findAllByCreator_IdOrCreatorIsNull(UUID creatorId);

    List<WorkoutPlan> findWorkoutPlansByCreator_IdOrCreator_IDIsNull(UUID creatorId);
}
