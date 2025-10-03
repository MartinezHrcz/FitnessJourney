package hu.hm.fitjourneyapi.mapper;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.fitness.UserWithWorkoutsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithFriendsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithPostsDTO;
import hu.hm.fitjourneyapi.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDTO toUserDTO(User user);

    List<UserDTO> toUserDTOList(List<User> users);

    User toUser(UserCreateDTO userCreateDTO);

    UserWithWorkoutsDTO toUserWithWorkoutsDTO(User user);

    UserWithPostsDTO toUserWithPostsDTO(User user);

    UserWithFriendsDTO toUserWithFriendsDTO(User user);

}
