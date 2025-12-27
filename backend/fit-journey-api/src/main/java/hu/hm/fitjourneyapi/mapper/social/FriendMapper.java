package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendCreateDTO;
import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendMapper {

    default FriendDTO toFriendDTO(Friend friend, UUID viewerId) {
        if (friend == null) return null;

        boolean sentByMe = friend.getUser().getId().equals(viewerId);

        User otherUser = sentByMe ? friend.getFriend() : friend.getUser();

        return FriendDTO.builder()
                .id(friend.getId())
                .userId(friend.getUser().getId())
                .friendId(friend.getFriend().getId())
                .friendName(otherUser.getName())
                .friendEmail(otherUser.getEmail())
                .isRequester(sentByMe)
                .status(friend.getStatus())
                .requestedTime(friend.getRequestedTime())
                .build();
    }

    default List<FriendDTO> toListFriendDTO(List<Friend> friends, UUID viewerId) {
        return friends.stream()
                .map(f -> toFriendDTO(f, viewerId))
                .collect(Collectors.toList());
    }

    @Mapping(source="id", target = "id")
    @Mapping(source="user", target= "userId", qualifiedByName = "userToId")
    @Mapping(source="friend", target= "friendId", qualifiedByName = "userToId")
    FriendDTO toFriendDTO(Friend friend);

    List<FriendDTO> toListFriendDTO(List<Friend> friends);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "user", expression = "java(user)")
    @Mapping(target = "friend", expression = "java(friend)")
    Friend toFriend(FriendDTO dto, User user, User friend);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(user)")
    @Mapping(target = "friend", expression = "java(friend)")
    Friend toFriend(FriendCreateDTO dto, User user, User friend);

    @Named("userToId")
    static UUID userToId(User user) {
        return user != null ? user.getId() : null;
    }
}
