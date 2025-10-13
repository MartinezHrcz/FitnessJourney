package hu.hm.fitjourneyapi.service.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.mapper.social.FriendMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Friend;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.FriendRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.FriendService;
import hu.hm.fitjourneyapi.utils.FriendsTestFactory;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class FriendServiceTests {

    @Autowired
    private FriendService friendService;

    @MockitoBean
    private FriendRepository friendRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private FriendMapper friendMapper;

    private Friend relationship;
    private FriendDTO relationshipDTO;
    private User sender;
    private User recipient;

    @BeforeEach
    void setUp(){
        sender = UserTestFactory.getUser();
        sender.setId(1);
        recipient = UserTestFactory.getUser();
        recipient.setId(2);

        relationship = FriendsTestFactory.getFriend(sender);
        relationshipDTO = FriendsTestFactory.getFriendDTO();
    }


}
