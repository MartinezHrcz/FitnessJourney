package hu.hm.fitjourneyapi.service.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendCreateDTO;
import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.exception.social.friend.FriendNotFoundException;
import hu.hm.fitjourneyapi.mapper.social.FriendMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import hu.hm.fitjourneyapi.model.social.Friend;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.FriendRepository;
import hu.hm.fitjourneyapi.services.implementation.social.FriendServiceImpl;
import hu.hm.fitjourneyapi.utils.FriendsTestFactory;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FriendServiceTests {

    @Mock
    private FriendRepository friendRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FriendMapper friendMapper;

    @InjectMocks
    private FriendServiceImpl friendService;

    private User sender;
    private User recipient;
    private Friend relationship;
    private FriendDTO relationshipDTO;

    @BeforeEach
    void setUp() {
        sender = UserTestFactory.getUser();
        sender.setId(UUID.randomUUID());

        recipient = UserTestFactory.getUser();
        recipient.setId(UUID.randomUUID());

        relationship = FriendsTestFactory.getFriend(sender);
        relationship.setFriend(recipient);
        relationship.setId(UUID.randomUUID());

        relationshipDTO = FriendsTestFactory.getFriendDTO();
        relationshipDTO.setUserId(sender.getId());
        relationshipDTO.setFriendId(recipient.getId());
    }

    @Test
    void getFriends_Success() {
        when(friendRepository.findAll()).thenReturn(List.of(relationship));
        when(friendMapper.toListFriendDTO(anyList())).thenReturn(List.of(relationshipDTO));

        List<FriendDTO> result = friendService.getFriends();

        assertFalse(result.isEmpty());
        verify(friendRepository).findAll();
    }

    @Test
    void getFriendsByUserId_Success() {
        UUID searchId = sender.getId();
        when(friendRepository.findFriendsByUser_IdOrFriend_Id(searchId, searchId)).thenReturn(List.of(relationship));
        when(friendMapper.toListFriendDTO(anyList(), eq(searchId))).thenReturn(List.of(relationshipDTO));

        List<FriendDTO> result = friendService.getFriendsByUserId(searchId);

        assertNotNull(result);
        verify(friendRepository).findFriendsByUser_IdOrFriend_Id(searchId, searchId);
    }

    @Test
    void updateFriend_Success() {
        when(friendRepository.findById(relationship.getId())).thenReturn(Optional.of(relationship));
        when(friendRepository.save(any(Friend.class))).thenReturn(relationship);
        when(friendMapper.toFriendDTO(any(Friend.class))).thenReturn(relationshipDTO);

        FriendDTO result = friendService.updateFriend(relationship.getId(), FriendStatus.ACCEPTED);

        assertNotNull(result);
        verify(friendRepository).save(relationship);
    }

    @Test
    void createFriend_Success() {
        FriendCreateDTO createDTO = new FriendCreateDTO(sender.getId(), recipient.getId());

        when(userRepository.existsById(sender.getId())).thenReturn(true);
        when(userRepository.existsById(recipient.getId())).thenReturn(true);
        when(friendRepository.existsByFriend_IdAndUser_Id(any(), any())).thenReturn(false);

        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findById(recipient.getId())).thenReturn(Optional.of(recipient));
        when(friendRepository.save(any(Friend.class))).thenReturn(relationship);
        when(friendMapper.toFriendDTO(any(Friend.class), eq(sender.getId()))).thenReturn(relationshipDTO);

        FriendDTO result = friendService.createFriend(createDTO);

        assertNotNull(result);
        verify(friendRepository).save(any(Friend.class));
    }

    @Test
    void createFriend_AlreadyExists_ThrowsException() {
        FriendCreateDTO createDTO = new FriendCreateDTO(sender.getId(), recipient.getId());

        when(userRepository.existsById(any())).thenReturn(true);
        when(friendRepository.existsByFriend_IdAndUser_Id(sender.getId(), recipient.getId())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> friendService.createFriend(createDTO));
    }

    @Test
    void deleteFriend_Success() {
        when(friendRepository.findById(relationship.getId())).thenReturn(Optional.of(relationship));

        friendService.deleteFriend(relationship.getId());

        verify(friendRepository).delete(relationship);
    }

    @Test
    void deleteFriend_NotFound_ThrowsException() {
        when(friendRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(FriendNotFoundException.class, () -> friendService.deleteFriend(UUID.randomUUID()));
    }

}
