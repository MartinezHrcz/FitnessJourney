package hu.hm.fitjourneyapi.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public abstract class AbstractUserDTO {
    private String name;
    private String email;
    private float weightInKg;
    private float heightInCm;
}
