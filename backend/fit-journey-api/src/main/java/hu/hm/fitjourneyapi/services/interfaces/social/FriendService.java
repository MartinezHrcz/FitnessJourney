package hu.hm.fitjourneyapi.services.interfaces.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;

import java.util.List;
import java.util.UUID;

public interface FriendService {
    FriendDTO getFriendById(UUID id);

    List<FriendDTO> getFriends();

    List<FriendDTO> getFriendsByUserId(UUID id);

    List<FriendDTO> getFriendsByUserIdAndRecipientName(UUID id, String recipientName);

    FriendDTO updateFriend(UUID id,FriendDTO friendDTO);

    FriendDTO createFriend(FriendDTO friendDTO);

    void deleteFriend(UUID id);
}
