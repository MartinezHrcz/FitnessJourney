package hu.hm.fitjourneyapi.dto.fitness.set;

import hu.hm.fitjourneyapi.model.enums.SetType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class FlexibilitySetDTO extends AbstractSetDTO {
    private int reps;

    @Override
    public SetType getType() {
        return SetType.FLEXIBILITY;
    }
}
