package hu.hm.fitjourneyapi.dto.user;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
public abstract class AbstractUserDTO {
    private String name;
    private String email;
    private float weightInKg;
    private float heightInCm;
}
