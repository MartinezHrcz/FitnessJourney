package hu.hm.fitjourneyapi.dto.fitness.set;

import hu.hm.fitjourneyapi.model.enums.SetType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class CardioSetDTO extends AbstractSetDTO{
    private int durationInSeconds;
    private double distanceInKilometers;

    @Override
    public SetType getType() {
        return  SetType.CARDIO;
    }
}
