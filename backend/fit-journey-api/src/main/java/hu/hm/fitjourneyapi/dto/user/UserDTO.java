package hu.hm.fitjourneyapi.dto.user;

import hu.hm.fitjourneyapi.model.enums.Roles;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
@NoArgsConstructor
public class UserDTO extends AbstractUserDTO {
    private long id;
    private Roles role;
}
