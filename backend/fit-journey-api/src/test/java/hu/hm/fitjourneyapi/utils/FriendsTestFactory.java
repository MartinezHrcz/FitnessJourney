package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import hu.hm.fitjourneyapi.model.enums.Roles;
import hu.hm.fitjourneyapi.model.social.Friend;
import org.h2.engine.Role;
import org.springframework.util.SerializationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class FriendsTestFactory {

    public static FriendDTO getFriendDTO() {
        FriendDTO friendDTO =
                FriendDTO.builder()
                        .friendId(UUID.randomUUID())
                        .userId(UUID.randomUUID())
                        .requestedTime(LocalDateTime.MIN)
                        .status(FriendStatus.ACCEPTED)
                        .build();
        return friendDTO;
    }

    public static Friend getFriend(User user1) {
        User user2 = User.builder()
                .id(UUID.randomUUID())
                .name("Friend Name")
                .email("friend@gmail.com")
                .birthday(LocalDate.of(1991, 1,1))
                .role(Roles.USER)
                .heightInCm(180)
                .weightInKg(100)
                .build();

        Friend friend = Friend
                .builder()
                .user(user1)
                .friend(user2)
                .status(FriendStatus.ACCEPTED)
                .build();
        return friend;
    }

}
