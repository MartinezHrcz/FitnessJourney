package hu.hm.fitjourneyapi.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractUserDTO {
    private String name;
    private String email;
    private LocalDate birthday;
    private float weightInKg;
    private float heightInCm;
}
