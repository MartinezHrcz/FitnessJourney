package hu.hm.fitjourneyapi.dto.fitness.set;

import hu.hm.fitjourneyapi.model.enums.SetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractSetDTO {
    private long id;
    private long exerciseId;
    public abstract SetType getType();
}
