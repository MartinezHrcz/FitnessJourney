package hu.hm.fitjourneyapi.dto.fitness.workoutPlan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class WorkoutPlanCreateDTO {
    @NotBlank
    @Size(max = 50)
    private String name;

    @Size(max = 200)
    private String description;

    @NotNull
    private UUID userId;

    @NotEmpty
    private List<PlanExerciseRequestDTO> exercises;
}
