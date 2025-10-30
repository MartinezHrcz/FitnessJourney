package hu.hm.fitjourneyapi.dto.user;

import hu.hm.fitjourneyapi.model.enums.Roles;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@Getter
@SuperBuilder
@NoArgsConstructor
public class UserDTO extends AbstractUserDTO {
    private UUID id;
    private Roles role;
    private String token;
}
