package hu.hm.fitjourneyapi.dto.fitness.workoutPlan;

import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanExerciseDTO {
    private UUID id;
    private UUID exerciseTemplateId;
    private String name;
    private ExerciseTypes type;
    private Integer targetSets;
}