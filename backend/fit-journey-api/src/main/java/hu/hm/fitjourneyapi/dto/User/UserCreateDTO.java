package hu.hm.fitjourneyapi.dto.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDTO extends AbstractUserDTO{
    private String password;
    private LocalDate birthday;
}
