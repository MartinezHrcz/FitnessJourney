package hu.hm.fitjourneyapi.dto.user.social;

import hu.hm.fitjourneyapi.dto.user.AbstractUserDTO;
import hu.hm.fitjourneyapi.model.social.Friend;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithFriendsDTO extends AbstractUserDTO {
    private long id;
    private List<Friend> friends;
}
