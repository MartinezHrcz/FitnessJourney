package hu.hm.fitjourneyapi.dto.fitness.workoutPlan;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PlanExerciseRequestDTO {
    @NotNull
    private UUID exerciseTemplateId;
    @Min(1)
    private Integer targetSets;
}