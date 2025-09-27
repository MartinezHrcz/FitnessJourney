package hu.hm.fitjourneyapi.dto.user;

import hu.hm.fitjourneyapi.model.enums.Roles;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends AbstractUserDTO {
    private long id;
    private LocalDateTime birthday;
    private Roles role;
}
