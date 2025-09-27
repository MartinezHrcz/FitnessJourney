package hu.hm.fitjourneyapi.dto.user.social;

import hu.hm.fitjourneyapi.dto.user.AbstractUserDTO;
import hu.hm.fitjourneyapi.model.social.Friend;

import java.util.List;

public class UserWithFriendsDTO extends AbstractUserDTO {
    private long id;
    private List<Friend> friends;
}
