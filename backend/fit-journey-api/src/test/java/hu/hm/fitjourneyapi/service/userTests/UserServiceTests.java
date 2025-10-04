package hu.hm.fitjourneyapi.service.userTests;


import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreateDTO userCreateDTO;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setup() {
        userCreateDTO = UserTestFactory.getUserCreateDTO();
        user = UserTestFactory.getUser();
        userDTO = UserTestFactory.getUserDTO();

        when(userMapper.toUser(userDTO)).thenReturn(user);
        when(userMapper.toUser(userCreateDTO)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(passwordEncoder.encode(userCreateDTO.getPassword())).thenReturn("Encodedpassword123!");
        when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    void testCreateUser_success() {
        UserDTO result = userService.createUser(userCreateDTO);
        assertNotNull(result);
        assertEquals(UserTestFactory.getUserCreateDTO().getName(), result.getName());
        assertEquals(UserTestFactory.getUserCreateDTO().getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    void testCreateUser_fail() {
    }

    void testUpdateUser_success() {

    }

    void testUpdateUser_fail() {

    }

    void testUpdateUserPassword_success() {

    }

    void testUpdateUserPassword_fail() {

    }

    void testDeleteUser_success() {

    }

    void testDeleteUser_fail() {

    }

    void testGetAllUsers_success() {

    }

    void testGetUserWithWorkout_success() {

    }

    void testGetUserWithFriends_success() {

    }

    void testGetUserWithPosts_success() {

    }


    void testGetUserById_success() {

    }

    void testGetUserById_fail() {

    }

    void testGetUserByEmail_success() {

    }

    void testGetUserByUsername_success() {

    }



}
