package hu.hm.fitjourneyapi.dto.User;

import hu.hm.fitjourneyapi.model.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends AbstractUserDTO {
    private long id;
    private LocalDateTime birthday;
    private Roles role;
}
