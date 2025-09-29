package hu.hm.fitjourneyapi.mapper;

import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.model.User;

public class UserMapper {
    public static UserDTO toUserDTO(final User user) {
        if (user == null) return null;
        return UserDTO.builder()
                        .id(user.getId())
                        .birthday(user.getBirthday())
                        .role(user.getRole())
                        .build();
    }
}
