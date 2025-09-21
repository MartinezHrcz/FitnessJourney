package hu.hm.fitjourneyapi.repository.socialTest;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import hu.hm.fitjourneyapi.model.social.Friend;
import hu.hm.fitjourneyapi.repository.social.FriendRepository;
import hu.hm.fitjourneyapi.repository.testutil.TestSocialDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@DataJpaTest
@ComponentScan(value = "hu.hm.fitjourneyapi.repository.testutil",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class))
public class FriendRepositoryTest {
    @Autowired
    private TestSocialDataFactory factory;

    @Autowired
    private FriendRepository friendRepository;

    private User user1, user2;
    private Friend friend;

    @BeforeEach
    void setup() {
        user1 = factory.createUser();
        user2 = factory.createUser();
        friend = factory.createFriend(user1, user2);
    }

    @Test
    void testSaveFriend() {
        assertEquals(user1, friend.getUser());
        assertEquals(user2, friend.getFriend());
        assertEquals(friend, friendRepository.getReferenceById(friend.getId()));
    }

    @Test
    void testDeleteFriend() {
        friendRepository.deleteById(friend.getId());
        assertEquals(Optional.empty(), friendRepository.findById(friend.getId()));
    }

    @Test
    void testUpdateFriend() {
        friend.setStatus(FriendStatus.ACCEPTED);
        friendRepository.save(friend);
        assertEquals(friend, friendRepository.getReferenceById(friend.getId()));
        friend.setStatus(FriendStatus.DECLINED);
        friendRepository.save(friend);
        assertEquals(friend, friendRepository.getReferenceById(friend.getId()));
    }
}
