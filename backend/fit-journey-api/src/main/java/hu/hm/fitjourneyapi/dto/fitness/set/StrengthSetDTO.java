package hu.hm.fitjourneyapi.dto.fitness.set;

import hu.hm.fitjourneyapi.model.enums.SetType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Setter
@NoArgsConstructor
public class StrengthSetDTO extends AbstractSetDTO{
    private int reps;
    private int weight;

    @Override
    public SetType getType() {
        return SetType.STRENGTH;
    }
}