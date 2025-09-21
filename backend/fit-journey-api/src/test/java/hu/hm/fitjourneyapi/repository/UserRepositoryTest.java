package hu.hm.fitjourneyapi.repository;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.repository.testutil.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ComponentScan(value = "hu.hm.fitjourneyapi.repository.testutil",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class))
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestDataFactory factory;

    private User user;

    @BeforeEach
    void setup(){
        user = factory.createUser();
    }

    @Test
    void testSaveUser(){
        assertFalse(userRepository.findAll().isEmpty(), "List shouldn't be empty");
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
