package hu.hm.fitjourneyapi.repository;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.repository.testutil.TestFitnessDataFactory;
import hu.hm.fitjourneyapi.repository.testutil.TestSocialDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@Import({TestFitnessDataFactory.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestFitnessDataFactory factory;

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
        user.setName("TestNameUpdated");
        user.setEmail("TestEmailUpdated");
        user.setBirthday(LocalDate.of(2001, 3,2));
        user.setPassword("123TestUpdated");
        user.setWeightInKg(110);
        user.setHeightInCm(110);
        userRepository.save(user);
        assertEquals(user, userRepository.getUserById(user.getId()));
    }

    @Test
    void testDeleteUser(){
        assertTrue(userRepository.findById(user.getId()).isPresent());
        userRepository.deleteById(user.getId());
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }



}
