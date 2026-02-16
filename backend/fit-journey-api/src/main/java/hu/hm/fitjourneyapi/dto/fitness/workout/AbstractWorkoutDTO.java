package hu.hm.fitjourneyapi.dto.fitness.workout;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
@NoArgsConstructor
@Setter
public abstract class AbstractWorkoutDTO {
    private String name;
    private String description;
    private UUID userId;
}