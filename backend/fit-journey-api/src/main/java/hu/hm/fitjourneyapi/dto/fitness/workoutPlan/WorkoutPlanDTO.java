package hu.hm.fitjourneyapi.dto.fitness.workoutPlan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutPlanDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID creatorId;
    private List<PlanExerciseDTO> exercises;
}