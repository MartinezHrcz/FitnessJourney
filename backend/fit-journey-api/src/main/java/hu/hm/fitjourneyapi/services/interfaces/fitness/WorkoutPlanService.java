package hu.hm.fitjourneyapi.services.interfaces.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workoutPlan.WorkoutPlanCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workoutPlan.WorkoutPlanDTO;

import java.util.List;
import java.util.UUID;

public interface WorkoutPlanService {

    WorkoutPlanDTO createPlan(WorkoutPlanCreateDTO dto);

    WorkoutPlanDTO getPlanById(UUID planId);

    List<WorkoutPlanDTO> getPlanByName(String planName);

    List<WorkoutPlanDTO> getAvailablePlans(UUID userId);

    void deletePlan(UUID planId, UUID userId);

    UUID startWorkoutFromPlan(UUID planId, UUID userId);
}
