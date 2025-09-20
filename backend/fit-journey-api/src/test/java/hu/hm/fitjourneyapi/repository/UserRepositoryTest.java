package hu.hm.fitjourneyapi.repository;

import hu.hm.fitjourneyapi.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser(){
        User user = User.builder()
                .name("TestName")
                .email("TestEmail")
                .birthday(new Date(2000, 1,1))
                .password("123Test")
                .weightInKg(100)
                .heightInCm(180)
                .build();
        userRepository.save(user);
        assert userRepository.findById(user.getId()).isPresent();
    }
}
