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
    public FriendDTO updateFriend(UUID id, FriendStatus status) {
        log.debug("Attempting to update friend with id {} ", id);
        Friend friend = friendRepository.findById(id).orElseThrow(
                ()->{
                    log.warn("Friend with id {} not found", id);
                    return new FriendNotFoundException("Friend with id " + id + " not found");
                }
        );
        friend.setStatus(status);
        friend = friendRepository.save(friend);
        return friendMapper.toFriendDTO(friend);
    }

    @Override
    public FriendDTO createFriend(FriendCreateDTO friendDTO) {
        boolean usersExist = userRepository.existsById(friendDTO.getUserId()) &&
                userRepository.existsById(friendDTO.getFriendId());

        if (!usersExist) {
            throw new IllegalArgumentException("Users don't exist");
        }

        boolean exists = friendRepository.existsByFriend_IdAndUser_Id(friendDTO.getUserId(), friendDTO.getFriendId()) ||
                friendRepository.existsByFriend_IdAndUser_Id(friendDTO.getFriendId(), friendDTO.getUserId());

        if (exists) {
            throw new IllegalStateException("Relationship already exists");
        }

        User sender = userRepository.findById(friendDTO.getUserId()).orElseThrow(() -> new UserNotFound("Sender not found"));
        User recipient = userRepository.findById(friendDTO.getFriendId()).orElseThrow(() -> new UserNotFound("Recipient not found"));

        Friend friendship = Friend.builder()
                .user(sender)
                .friend(recipient)
                .status(FriendStatus.IN_PROGRESS)
                .requestedTime(LocalDateTime.now())
                .build();

        friendship = friendRepository.save(friendship);
        return friendMapper.toFriendDTO(friendship,sender.getId());
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
