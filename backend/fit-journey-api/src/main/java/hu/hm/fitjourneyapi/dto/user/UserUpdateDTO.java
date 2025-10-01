package hu.hm.fitjourneyapi.dto.user;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO extends AbstractUserDTO {
    private long id;

}
