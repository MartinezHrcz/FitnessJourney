package hu.hm.fitjourneyapi.service.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendCreateDTO;
import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.exception.social.friend.FriendNotFoundException;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.social.FriendMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
/*
    @BeforeEach
    void setUp() {
        sender = UserTestFactory.getUser();
        sender.setId(UUID.randomUUID());
        recipient = UserTestFactory.getUser();
        recipient.setId(UUID.randomUUID());

        relationship = FriendsTestFactory.getFriend(sender);
        relationshipDTO = FriendsTestFactory.getFriendDTO();

        when(friendRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(relationship));
        when(friendRepository.save(any(Friend.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.findById(eq(relationshipDTO.getUserId()))).thenReturn(Optional.of(sender));
        when(userRepository.findById(eq(relationshipDTO.getFriendId()))).thenReturn(Optional.of(recipient));
        when(friendRepository.findFriendsByUser_Id(any(UUID.class))).thenReturn(List.of(relationship));
        when(friendRepository.findFriendsByUser_Id(any(UUID.class))).thenReturn(List.of(relationship));
        when(friendRepository.findAll()).thenReturn(List.of(relationship));
        when(friendRepository.findFriendsByUser_IdAndFriend_Name(any(UUID.class), any(String.class))).thenReturn(List.of(relationship));
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

        when(friendMapper.toFriend(any(FriendDTO.class), any(User.class), any(User.class))).thenAnswer(
                invocation -> {
                    FriendDTO friend = invocation.getArgument(0);
                    return Friend.builder()
                            .id(friend.getId())
                            .status(friend.getStatus())
                            .user(sender)
                            .friend(recipient)
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
        UUID userId = sender.getId();
        String recipientName = recipient.getName();
        List<FriendDTO> result = friendService.getFriendsByUserId(userId);
        assertNotNull(result);
        assertEquals(relationshipDTO.getFriendId(), result.getFirst().getFriendId());
        assertEquals(relationshipDTO.getStatus(), result.getFirst().getStatus());
        assertEquals(relationshipDTO.getUserId(), result.getFirst().getUserId());
    }

    @Test
    public void updateFriend_updated_success() {
        FriendDTO update = FriendDTO
                .builder()
                .status(FriendStatus.DECLINED)
                .build();
        FriendDTO result = friendService.updateFriend(relationship.getId(), FriendStatus.DECLINED);
        assertEquals(update.getStatus(), result.getStatus());
    }

    @Test
    public void updateFriend_relationNotFound_fail() {
        when(friendRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(FriendNotFoundException.class, ()-> friendService.updateFriend(relationship.getId(), FriendStatus.ACCEPTED));
    }

    /*
    @Test
    public void createFriend_success() {
        FriendDTO result = friendService.createFriend(relationshipDTO);
        assertNotNull(result);
        assertEquals(relationshipDTO.getStatus(), result.getStatus());
    }

    @Test
    public void createFriend_userNotFound_fail() {
        when(userRepository.findById(relationshipDTO.getUserId())).thenReturn(Optional.empty());
        assertThrows(UserNotFound.class,()->friendService.createFriend(new FriendCreateDTO()));
    }

    @Test
    public void deleteFriend_success() {
        friendService.deleteFriend(relationship.getId());
        verify(friendRepository, times(1)).delete(any(Friend.class));
    }

    @Test
    public void deleteFriend_userNotFound_fail() {
        when(friendRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(FriendNotFoundException.class,()->friendService.deleteFriend(relationship.getId()));
    }

     */
}
