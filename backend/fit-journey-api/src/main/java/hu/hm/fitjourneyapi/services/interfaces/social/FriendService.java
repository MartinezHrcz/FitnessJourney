package hu.hm.fitjourneyapi.services.interfaces.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendCreateDTO;
import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;

import java.util.List;
import java.util.UUID;

public interface FriendService {
    FriendDTO getFriendById(UUID id);

    List<FriendDTO> getFriends();

    List<FriendDTO> getFriendsByUserId(UUID id);

    FriendDTO updateFriend(UUID id, FriendStatus status);

    FriendDTO createFriend(FriendCreateDTO friendDTO);

    void deleteFriend(UUID id);
}
