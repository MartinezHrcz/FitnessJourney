package hu.hm.fitjourneyapi.service.user;


import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserPasswordUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.exception.userExceptions.IncorrectPassword;
import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.Roles;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.repository.social.FriendRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.security.JwtUtil;
import hu.hm.fitjourneyapi.services.implementation.UserServiceImpl;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private PostRepository postRepository;
    @Mock
    private FriendRepository friendRepository;
    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;
    private UserCreateDTO userCreateDTO;

    @BeforeEach
    void setup() {
        user = UserTestFactory.getUser();
        user.setId(UUID.randomUUID());
        user.setRole(Roles.USER);

        userDTO = UserTestFactory.getUserDTO();
        userCreateDTO = UserTestFactory.getUserCreateDTO();
    }

    @Test
    void createUser_Success() {
        when(userMapper.toUser(userCreateDTO)).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.createUser(userCreateDTO);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_Success() {
        UserUpdateDTO updateDTO = UserUpdateDTO.builder().name("New Name").email("new@email.com").birthday(LocalDate.now()).weightInKg(80).heightInCm(180).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(jwtUtil.generateToken(any(), any(), any())).thenReturn("mock-token");

        UserDTO result = userService.updateUser(user.getId(), updateDTO);

        assertNotNull(result);
        assertEquals("mock-token", result.getToken());
        verify(userRepository).save(user);
    }

    @Test
    void updatePassword_Success() {
        UserPasswordUpdateDTO passDTO = new UserPasswordUpdateDTO("oldPass", "newPass");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), passDTO.getPasswordOld())).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(jwtUtil.generateToken(any(), any(), any())).thenReturn("new-token");

        UserDTO result = userService.updatePassword(user.getId(), passDTO);

        assertNotNull(result);
        verify(passwordEncoder).encode("newPass");
    }

    @Test
    void updatePassword_WrongOldPassword_ThrowsException() {
        UserPasswordUpdateDTO passDTO = new UserPasswordUpdateDTO("wrongOld", "newPass");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        assertThrows(IncorrectPassword.class, () -> userService.updatePassword(user.getId(), passDTO));
    }

    @Test
    void deleteUser_Success() {
        when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());

        verify(userRepository).delete(user);
    }

    @Test
    void getAllUsersByName_FiltersCorrectly() {
        User user1 = new User(); user1.setName("Alice");
        User user2 = new User(); user2.setName("Bob");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userMapper.toUserDTOList(anyList())).thenAnswer(i -> List.of(new UserDTO()));

        List<UserDTO> result = userService.getAllUsersByName("Ali");

        assertEquals(1, result.size());
    }
}
