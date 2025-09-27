package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractWorkoutDTO {
    private String name;
    private String description;
    private long userId;
}
