package hu.hm.fitjourneyapi.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserDTO extends AbstractUserDTO {
    private UUID id;
    private String token;
}
