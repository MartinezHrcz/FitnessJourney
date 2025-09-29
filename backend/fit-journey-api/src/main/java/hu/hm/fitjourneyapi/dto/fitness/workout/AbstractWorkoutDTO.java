package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractWorkoutDTO {
    private String name;
    private String description;
    private long userId;
}
