package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public abstract class AbstractWorkoutDTO {
    private String name;
    private String description;
    private long userId;
}
