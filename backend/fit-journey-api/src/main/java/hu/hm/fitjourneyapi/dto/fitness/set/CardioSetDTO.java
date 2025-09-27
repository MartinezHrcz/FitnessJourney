package hu.hm.fitjourneyapi.dto.fitness.set;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public class CardioSetDTO extends AbstractSetDTO{
    private int durationInSeconds;
    private double distanceInKilometers;
}
