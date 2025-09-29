package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Friend;

import java.util.List;
import java.util.stream.Collectors;

public class FriendMapper {
    public static FriendDTO toFriendDTO(Friend friend) {
        if (friend == null) return null;
        return FriendDTO.builder()
                .id(friend.getId())
                .userId(friend.getUser().getId())
                .friendId(friend.getFriend().getId())
                .status(friend.getStatus())
                .requestedTime(friend.getRequestedTime())
                .build();
    }

    public static List<FriendDTO> toListFriendDTO(List<Friend> friends) {
        if (friends == null) return null;
        return friends.stream().map(FriendMapper::toFriendDTO).collect(Collectors.toList());
    }

    public static Friend toFriend(FriendDTO friendDTO, User user, User friend  ) {
        return Friend.builder()
                .user(user)
                .friend(friend)
                .status(friendDTO.getStatus())
                .requestedTime(friendDTO.getRequestedTime())
                .build();
    }
}
