package hu.hm.fitjourneyapi.dto.user;

import hu.hm.fitjourneyapi.model.enums.Roles;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
public class UserDTO extends AbstractUserDTO {
    private long id;
    private LocalDate birthday;
    private Roles role;
}
