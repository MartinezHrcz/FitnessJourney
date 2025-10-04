package hu.hm.fitjourneyapi.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class UserCreateDTO extends AbstractUserDTO{
    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 8, max=40, message = "Password must be between ")
    private String password;
}
