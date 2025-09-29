package hu.hm.fitjourneyapi.mapper;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toUserDTO(final User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .heightInCm(user.getHeightInCm())
                .weightInKg(user.getWeightInKg())
                .build();
    }

    public static List<UserDTO> toUserDTOList(final List<User> users) {
        if (users == null) return null;
        return users.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());
    }

    public static User toUser(final UserCreateDTO userCreateDTO) {
        if (userCreateDTO == null) return null;
        return User.builder()
                .name(userCreateDTO.getName())
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword())
                .heightInCm(userCreateDTO.getHeightInCm())
                .weightInKg(userCreateDTO.getWeightInKg())
                .birthday(userCreateDTO.getBirthday())
                .build();
    }
}
