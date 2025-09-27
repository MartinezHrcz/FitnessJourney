package hu.hm.fitjourneyapi.dto.user;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Builder
public class UserCreateDTO extends AbstractUserDTO{
    private String password;
    private LocalDate birthday;
}
