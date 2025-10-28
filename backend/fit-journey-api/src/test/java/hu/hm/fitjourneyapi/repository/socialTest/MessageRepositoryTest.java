package hu.hm.fitjourneyapi.repository.socialTest;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Message;
import hu.hm.fitjourneyapi.repository.social.MessageRepository;
import hu.hm.fitjourneyapi.repository.testutil.TestSocialDataFactory;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Optional;

@DataJpaTest
@Import({TestSocialDataFactory.class})

public class MessageRepositoryTest {
    @Autowired
    private TestSocialDataFactory factory;

    @Autowired
    private MessageRepository messageRepository;

    private User user1,user2;
    private Message message;

    @BeforeEach
    public void setup() {
        user1 = factory.createUser("one");
        user2 = factory.createUser("two");
        message = factory.createMessage(user1, user2);
    }

    @Test
    void testSaveMessage() {
        assertEquals(message, messageRepository.findById(message.getId()).get());
    }

    @Test
    void testUpdateMessage() {
        message.setContent("Updated content");
        messageRepository.save(message);
        assertEquals(message, messageRepository.findById(message.getId()).get());
    }

    @Test
    void testDeleteMessage() {
        messageRepository.deleteById(message.getId());
        assertEquals(Optional.empty(), messageRepository.findById(message.getId()));
    }


}
