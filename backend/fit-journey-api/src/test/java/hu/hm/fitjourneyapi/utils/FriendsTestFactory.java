package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import hu.hm.fitjourneyapi.model.social.Friend;

import java.time.LocalDateTime;

public class FriendsTestFactory {

    public static FriendDTO getFriendDTO() {
        FriendDTO friendDTO =
                FriendDTO.builder()
                        .friendId(1L)
                        .userId(2L)
                        .requestedTime(LocalDateTime.MIN)
                        .status(FriendStatus.ACCEPTED)
                        .build();
        return friendDTO;
    }

    public static Friend getFriend(User user1) {
        User user2 = user1;
        user2.setId(2L);
        user2.setName("Friend Name");

        Friend friend = Friend
                .builder()
                .user(user1)
                .friend(user2)
                .status(FriendStatus.ACCEPTED)
                .build();
        return friend;
    }

}
