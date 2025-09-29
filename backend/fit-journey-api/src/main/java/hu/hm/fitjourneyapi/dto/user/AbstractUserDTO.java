package hu.hm.fitjourneyapi.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class AbstractUserDTO {
    private String name;
    private String email;
    private float weightInKg;
    private float heightInCm;
}
