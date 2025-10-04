package hu.hm.fitjourneyapi.service.userTests;


import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class UserServiceTests {

    private final UserService userService;

    @MockitoBean
    private UserMapper userMapper;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private UserRepository userRepository;

    public UserServiceTests(UserService userService) {
        this.userService = userService;
    }

    @Test
    void testCreateUser_success() {

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
