package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FriendMapper {
    @Mapping(source="user", target= "userId", qualifiedByName = "userToId")
    @Mapping(source="friend", target= "friendId", qualifiedByName = "userToId")
    FriendDTO toFriendDTO(Friend friend);

    List<FriendDTO> toListFriendDTO(List<Friend> friends);

    @Mapping(target = "user", expression = "java(user)")
    @Mapping(target = "friend", expression = "java(friend)")
    Friend toFriend(FriendDTO friendDTO, User user, User friend  );

    @Named("userToId")
    static Long userToId(User user) {
        return user != null ? user.getId() : null;
    }
}
