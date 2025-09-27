package hu.hm.fitjourneyapi.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO extends AbstractUserDTO {
    private long id;
}
