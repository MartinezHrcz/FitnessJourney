package hu.hm.fitjourneyapi.dto.fitness.set;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class CardioSetDTO extends AbstractSetDTO{
    private int durationInSeconds;
    private double distanceInKilometers;
}
