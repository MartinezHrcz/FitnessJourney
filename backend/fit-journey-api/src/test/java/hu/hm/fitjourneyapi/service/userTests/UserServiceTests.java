package hu.hm.fitjourneyapi.service.userTests;


import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserPasswordUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.fitness.UserWithWorkoutsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithFriendsDTO;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        when(userRepository.save(user)).thenAnswer(invocation -> invocation.getArgument(0));
        when(userMapper.toUserDTO(user)).thenAnswer(invocation ->
        {
            User u = invocation.getArgument(0);
            return UserDTO.builder()
                    .id(u.getId())
                    .name(u.getName())
                    .email(u.getEmail())
                    .weightInKg(u.getWeightInKg())
                    .heightInCm(u.getHeightInCm())
                    .birthday(u.getBirthday())
                    .build();
        });
    }

    @Test
    void testCreateUser_success() {
        UserDTO result = userService.createUser(userCreateDTO);
        assertNotNull(result);
        assertEquals(UserTestFactory.getUserCreateDTO().getName(), result.getName());
        assertEquals(UserTestFactory.getUserCreateDTO().getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_success() {
        UserUpdateDTO updateDTO = UserUpdateDTO.builder()
                .id(1L)
                .name("New name")
                .email("newEmail@gmail.com")
                .weightInKg(101)
                .heightInCm(190)
                .birthday(LocalDate.of(1991, 1, 1))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));

        UserDTO result = userService.updateUser(updateDTO);

        assertNotNull(result);

        assertEquals(updateDTO.getName(), result.getName());
        assertEquals(updateDTO.getEmail(), result.getEmail());
        assertEquals(updateDTO.getWeightInKg(), result.getWeightInKg());
        assertEquals(updateDTO.getHeightInCm(), result.getHeightInCm());
        assertEquals(updateDTO.getBirthday(), result.getBirthday());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserPassword_success() {
        UserPasswordUpdateDTO updateDTO = UserPasswordUpdateDTO.builder()
                .id(1L)
                .passwordNew("A123a!")
                .passwordOld("EncodedPassword123!")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(passwordEncoder.encode(anyString())).thenReturn("NewEncodedPassword123!");
        when(passwordEncoder.matches(updateDTO.getPasswordOld(), user.getPassword())).thenReturn(true);
        UserDTO result = userService.updatePassword(updateDTO);

        assertNotNull(result);
        assertEquals(1L,result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }


    @Test
    void testDeleteUser_success() {
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_fail() {
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.empty());
        assertThrows(UserNotFound.class, () -> userService.deleteUser(user.getId()));
    }

    @Test
    void testGetAllUsers_success() {
        when(userRepository.findAll()).thenReturn(UserTestFactory.getMultipleUsers());
        List<UserDTO> result = userService.getAllUsers();
        List<UserDTO> expected = userMapper.toUserDTOList(UserTestFactory.getMultipleUsers());
        assertNotNull(result);
        assertEquals(expected.size(), result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testGetUserWithWorkout_success() {
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserWithWorkoutsDTO(user)).thenReturn(UserTestFactory.getUserWithWorkoutsDTO());
        UserWithWorkoutsDTO result = userService.getUserWithWorkoutsById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getWeightInKg(), result.getWeightInKg());
        assertEquals(user.getWorkouts().getFirst().getName(), result.getWorkouts().getFirst().getName());
        assertEquals(user.getWorkouts().getFirst().getUser().getId(), result.getWorkouts().getFirst().getUserId());
    }

    @Test
    void testGetUserWithFriends_success() {
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserWithFriendsDTO(user)).thenReturn(UserTestFactory.getUserWithFriendsDTO());
        UserWithFriendsDTO result = userService.getUserWithFriendsById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(user.getId(), result.getFriends().getFirst().getUserId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getId(), result.getFriends().getFirst().getUserId());
        assertEquals(user.getFriends().getFirst().getFriend().getId(), result.getFriends().getFirst().getFriendId());
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
