package hu.hm.fitjourneyapi.repository;

import hu.hm.fitjourneyapi.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup(){
        User user = User.builder()
                .name("Placeholder")
                .email("Placeholder@email.com")
                .birthday(LocalDate.of(2000, 1,1))
                .password("PlaceholderPassword")
                .weightInKg(100)
                .heightInCm(180)
                .build();
        userRepository.save(user);
        userRepository.findAll().stream().forEach(System.out::println);
    }



    @Test
    void testSaveUser(){
        assertFalse(userRepository.findAll().isEmpty());
        assertEquals("Placeholder",userRepository.findAll().getFirst().getName());
    }

    @Test
    void testFindUserByEmail(){
        Optional<User> user = userRepository.findUserByEmail("Placeholder@email.com");
        assertTrue(user.isPresent());
        assertEquals("Placeholder",user.get().getName());
    }

    @Test
    void testUpdateUser(){
        List<User> users= userRepository.findAll();
        assertFalse(users.isEmpty(), "List should not be empty");
        User user = users.getFirst();
        user.setName("TestNameUpdated");
        user.setEmail("TestEmailUpdated");
        user.setBirthday(LocalDate.of(2001, 3,2));
        user.setPassword("123TestUpdated");
        user.setWeightInKg(110);
        user.setHeightInCm(110);
        userRepository.save(user);
        assertEquals("TestNameUpdated", user.getName());
    }

    @Test
    void testDeleteUser(){
        int id = userRepository.findAll().getFirst().getId();
        assertTrue(userRepository.findById(id).isPresent());
        userRepository.deleteById(userRepository.findById(id).get().getId());
        assertFalse(userRepository.findById(id).isPresent());
    }



}
