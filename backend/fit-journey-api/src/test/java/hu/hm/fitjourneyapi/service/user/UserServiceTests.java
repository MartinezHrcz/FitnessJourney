package hu.hm.fitjourneyapi.service.user;


import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserPasswordUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.exception.userExceptions.IncorrectPassword;
import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.CommentRepository;
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
    private CommentRepository commentRepository;
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
        UserUpdateDTO updateDTO = UserUpdateDTO.builder().name("  New Name  ").email("  new@email.com  ").birthday(LocalDate.now()).weightInKg(80f).heightInCm(180f).build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findUserByName("New Name")).thenReturn(Optional.empty());
        when(userRepository.findUserByEmail("new@email.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(jwtUtil.generateToken(any(), any())).thenReturn("mock-token");

        UserDTO result = userService.updateUser(user.getId(), updateDTO);

        assertNotNull(result);
        assertEquals("mock-token", result.getToken());
        assertEquals("New Name", user.getName());
        assertEquals("new@email.com", user.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_NullOptionalFields_SkipsThoseUpdates() {
        String originalName = user.getName();
        String originalEmail = user.getEmail();
        float originalWeight = user.getWeightInKg();
        float originalHeight = user.getHeightInCm();
        LocalDate originalBirthday = user.getBirthday();

        UserUpdateDTO updateDTO = UserUpdateDTO.builder()
                .name(null)
                .email(null)
                .birthday(null)
                .weightInKg(null)
                .heightInCm(null)
                .preferredCalories(null)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(jwtUtil.generateToken(any(), any())).thenReturn("mock-token");

        UserDTO result = userService.updateUser(user.getId(), updateDTO);

        assertNotNull(result);
        assertEquals(originalName, user.getName());
        assertEquals(originalEmail, user.getEmail());
        assertEquals(originalWeight, user.getWeightInKg());
        assertEquals(originalHeight, user.getHeightInCm());
        assertEquals(originalBirthday, user.getBirthday());
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_ExistingName_ThrowsException() {
        UUID otherUserId = UUID.randomUUID();
        User otherUser = new User();
        otherUser.setId(otherUserId);

        UserUpdateDTO updateDTO = UserUpdateDTO.builder()
                .name("TakenName")
                .email("new@email.com")
                .birthday(LocalDate.now())
                .weightInKg(80f)
                .heightInCm(180f)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findUserByName("TakenName")).thenReturn(Optional.of(otherUser));

        assertThrows(IllegalStateException.class, () -> userService.updateUser(user.getId(), updateDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_ExistingEmail_ThrowsException() {
        UUID otherUserId = UUID.randomUUID();
        User otherUser = new User();
        otherUser.setId(otherUserId);

        UserUpdateDTO updateDTO = UserUpdateDTO.builder()
                .name("New Name")
                .email("taken@email.com")
                .birthday(LocalDate.now())
                .weightInKg(80f)
                .heightInCm(180f)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findUserByName("New Name")).thenReturn(Optional.empty());
        when(userRepository.findUserByEmail("taken@email.com")).thenReturn(Optional.of(otherUser));

        assertThrows(IllegalStateException.class, () -> userService.updateUser(user.getId(), updateDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_BlankName_ThrowsException() {
        UserUpdateDTO updateDTO = UserUpdateDTO.builder()
                .name("   ")
                .email("valid@email.com")
                .birthday(LocalDate.now())
                .weightInKg(80f)
                .heightInCm(180f)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user.getId(), updateDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updatePassword_Success() {
        UserPasswordUpdateDTO passDTO = new UserPasswordUpdateDTO("oldPass", "newPass");
        String oldPassword = "EncodedPassword123!";

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPass", oldPassword)).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("encodedNewPass");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(jwtUtil.generateToken(any(), any())).thenReturn("new-token");

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
