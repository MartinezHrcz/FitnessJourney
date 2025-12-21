package hu.hm.fitjourneyapi.dto.fitness.workout;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor
public class WorkoutDTO extends AbstractWorkoutDTO {
    private UUID id;
    private int lengthInMins;
    private LocalDate startDate;
    private List<AbstractExerciseDTO> exercises;
}
