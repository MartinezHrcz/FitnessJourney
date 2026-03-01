package hu.hm.fitjourneyapi.services.implementation.social;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final UserRepository userRepository;

    public FriendServiceImpl(FriendRepository friendRepository, FriendMapper friendMapper, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.friendMapper = friendMapper;
        this.userRepository = userRepository;
    }

    @Override
    public FriendDTO getFriendById(UUID id) {
        log.debug("Fetching friend with id {} ", id);
        Friend friend = friendRepository.findById(id).orElseThrow(
                ()-> {
                    log.warn("Friend with id {} not found", id);
                    return new FriendNotFoundException("Friend with id " + id + " not found");
                }
        );
        log.info("Fetched friend with id {} ", id);
        return friendMapper.toFriendDTO(friend);
    }

    @Override
    public List<FriendDTO> getFriends() {
        log.debug("Fetching all friends");
        List<Friend> friends = friendRepository.findAll();
        log.info("Fetched all friends");
        return friendMapper.toListFriendDTO(friends);
    }

    @Override
    public List<FriendDTO> getFriendsByUserId(UUID id) {
        log.debug("Fetching all friends with user id {} ", id);
        List<Friend> friends = friendRepository.findFriendsByUser_IdOrFriend_Id(id,id);
        log.info("Fetched all friends with user id {} ", id);
        return friendMapper.toListFriendDTO(friends, id);
    }

    @Override
    public FriendDTO updateFriend(UUID id, FriendStatus status, UUID currentUserId) throws IllegalAccessException {
        log.debug("Attempting to update friend with id {} ", id);
        Friend friendship = friendRepository.findById(id).orElseThrow(
                ()->{
                    log.warn("Friend with id {} not found", id);
                    return new FriendNotFoundException("Friend with id " + id + " not found");
                }
        );

        if (status == FriendStatus.ACCEPTED && !friendship.getFriend().getId().equals(currentUserId)) {
            throw new IllegalAccessException("Only the recipient can accept a friend request");
        }

        friendship.setStatus(status);
        return friendMapper.toFriendDTO(friendRepository.save(friendship));
    }

    @Override
    public FriendDTO createFriend(FriendCreateDTO friendDTO) {
        UUID senderId = friendDTO.getUserId();
        UUID recipientId = friendDTO.getFriendId();

        if (senderId.equals(recipientId)) {
            throw new IllegalArgumentException("You cannot add yourself as a friend");
        }

        if (friendRepository.existsByFriend_IdAndUser_Id(senderId, recipientId) ||
                friendRepository.existsByFriend_IdAndUser_Id(recipientId, senderId)) {
            throw new IllegalStateException("Relationship already exists");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFound("Sender not found: " + senderId));
        User recipient = userRepository.findById(recipientId)
                .orElseThrow(() -> new UserNotFound("Recipient not found: " + recipientId));

        Friend friendship = Friend.builder()
                .user(sender)
                .friend(recipient)
                .status(FriendStatus.IN_PROGRESS)
                .requestedTime(LocalDateTime.now())
                .build();

        friendship = friendRepository.save(friendship);
        log.info("Friend request created from {} to {}", senderId, recipientId);
        return friendMapper.toFriendDTO(friendship, senderId);
    }

    @Override
    public void deleteFriend(UUID id) {
        Friend friend = friendRepository.findById(id).orElseThrow(
                ()->{
                    log.warn("Friend with id {} not found", id);
                    return new FriendNotFoundException("Friend with id " + id + " not found");
                }
        );
        friendRepository.delete(friend);
    }
}
