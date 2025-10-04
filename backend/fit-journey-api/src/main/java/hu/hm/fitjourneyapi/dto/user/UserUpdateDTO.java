package hu.hm.fitjourneyapi.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO extends AbstractUserDTO {
    @NotBlank(message = "Id shouldn't be blank")
    private long id;
}
