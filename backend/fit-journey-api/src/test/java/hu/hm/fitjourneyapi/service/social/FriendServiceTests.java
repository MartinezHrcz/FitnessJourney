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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    void setUp() {
        sender = UserTestFactory.getUser();
        sender.setId(1);
        recipient = UserTestFactory.getUser();
        recipient.setId(2);

        relationship = FriendsTestFactory.getFriend(sender);
        relationshipDTO = FriendsTestFactory.getFriendDTO();

        when(friendRepository.findById(any(long.class))).thenReturn(Optional.ofNullable(relationship));
        when(friendRepository.save(any(Friend.class))).thenReturn(relationship);
        when(userRepository.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepository.findById(2L)).thenReturn(Optional.of(recipient));
        when(friendRepository.findFriendsByUser_Id(any(long.class))).thenReturn(List.of(relationship));
        when(friendRepository.findFriendsByUser_Id(any(long.class))).thenReturn(List.of(relationship));
        when(friendRepository.findAll()).thenReturn(List.of(relationship));
        when(friendRepository.findFriendsByUser_IdAndFriend_Name(any(long.class), any(String.class))).thenReturn(List.of(relationship));

        when(friendMapper.toFriendDTO(any(Friend.class))).thenAnswer(
                invocation -> {
                    Friend friend = invocation.getArgument(0);
                    return FriendDTO.builder()
                            .id(friend.getId())
                            .status(friend.getStatus())
                            .userId(friend.getUser().getId())
                            .friendId(friend.getFriend().getId())
                            .build();
                }
        );

        when(friendMapper.toListFriendDTO(any(List.of(Friend.class).getClass()))).thenReturn(List.of(relationshipDTO));
    }

    @Test
    public void getFriendTest_getAll_success() {
        List<FriendDTO> result = friendService.getFriends();
        assertNotNull(result);
        assertEquals(relationshipDTO.getFriendId(), result.getFirst().getFriendId());
        assertEquals(relationshipDTO.getStatus(), result.getFirst().getStatus());
        assertEquals(relationshipDTO.getUserId(), result.getFirst().getUserId());
    }

    @Test
    public void getFriendsByUserId_getAll_success() {
        List<FriendDTO> result = friendService.getFriendsByUserId(sender.getId());
        assertNotNull(result);
        assertEquals(relationshipDTO.getFriendId(), result.getFirst().getFriendId());
        assertEquals(relationshipDTO.getStatus(), result.getFirst().getStatus());
        assertEquals(relationshipDTO.getUserId(), result.getFirst().getUserId());
    }

    @Test
    public void getFriendsByUserIdAndRecipient_getAll_success() {
        long userId = sender.getId();
        String recipientName = recipient.getName();
        List<FriendDTO> result = friendService.getFriendsByUserIdAndRecipientName(userId, recipientName);
        assertNotNull(result);
        assertEquals(relationshipDTO.getFriendId(), result.getFirst().getFriendId());
        assertEquals(relationshipDTO.getStatus(), result.getFirst().getStatus());
        assertEquals(relationshipDTO.getUserId(), result.getFirst().getUserId());
    }

    @Test
    public void updateFriend_updated_success() {

    }

    @Test
    public void updateFriend_relationNotFound_fail() {

    }

    @Test
    public void createFriend_success() {

    }
    @Test
    public void createFriend_userNotFound_fail() {

    }

    @Test
    public void deleteFriend_success() {

    }

    @Test
    public void deleteFriend_userNotFound_fail() {

    }
}
