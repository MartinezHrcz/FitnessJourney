package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.model.User;

import java.time.LocalDate;

public class UserTestFactory {
    public static UserCreateDTO getUserCreateDTO() {
        UserCreateDTO dto = UserCreateDTO.builder()
                .name("Test name")
                .email("user@gmail.com")
                .heightInCm(180)
                .weightInKg(100)
                .password("ExamplePassword123!")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
        return dto;
    }

    public static UserDTO getUserDTO() {
        UserDTO dto = UserDTO.builder()
                .id(1L)
                .name("Test name")
                .email("user@gmail.com")
                .heightInCm(180)
                .weightInKg(100)
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
        return dto;
    }

    public static User getUser() {
        User user = User.builder()
                .id(1L)
                .name("Test name")
                .email("user@gmail.com")
                .heightInCm(180)
                .weightInKg(100)
                .password("EncodedPassword123!")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
        return user;
    }
}
