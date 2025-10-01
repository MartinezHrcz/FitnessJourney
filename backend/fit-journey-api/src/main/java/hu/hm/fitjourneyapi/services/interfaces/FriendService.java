package hu.hm.fitjourneyapi.services.interfaces;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;

import java.util.List;

public interface FriendService {
    FriendDTO getFriendById(long id);
    List<FriendDTO> getFriends();
    List<FriendDTO> getFriendsByUserId(long id);
    List<FriendDTO> getFriendsByUserIdAndRecipientName(long id, String recipientName);
    FriendDTO updateFriend(FriendDTO friendDTO);
    FriendDTO createFriend(FriendDTO friendDTO);
    void deleteFriend(long id);

}
