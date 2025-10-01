package hu.hm.fitjourneyapi.dto.user;

import lombok.*;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserCreateDTO extends AbstractUserDTO{
    private String password;
}
