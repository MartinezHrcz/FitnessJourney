package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractWorkoutDTO {
    private String name;
    private String description;
    private long userId;
}
