package hu.hm.fitjourneyapi.dto.fitness.workout;

import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
public class WorkoutUpdateDTO extends AbstractWorkoutDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
