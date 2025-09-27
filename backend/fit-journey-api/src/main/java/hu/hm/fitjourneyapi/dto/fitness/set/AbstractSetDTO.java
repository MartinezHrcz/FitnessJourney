package hu.hm.fitjourneyapi.dto.fitness.set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public abstract class AbstractSetDTO {
    private long id;
    private long exerciseId;
}
