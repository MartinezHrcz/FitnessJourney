package hu.hm.fitjourneyapi.dto.fitness.workout;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.model.enums.WorkoutStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor
@Setter
public class WorkoutDTO extends AbstractWorkoutDTO {
    private UUID id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private WorkoutStatus status;
    private List<AbstractExerciseDTO> exercises;
}
