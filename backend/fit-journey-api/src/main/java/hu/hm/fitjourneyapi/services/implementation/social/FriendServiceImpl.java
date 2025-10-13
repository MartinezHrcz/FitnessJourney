package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.exception.social.friend.FriendNotFoundException;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.mapper.social.FriendMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Friend;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.FriendRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public FriendDTO getFriendById(long id) {
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
    public List<FriendDTO> getFriendsByUserId(long id) {
        log.debug("Fetching all friends with user id {} ", id);
        List<Friend> friends = friendRepository.findFriendsByUser_Id(id);
        log.info("Fetched all friends with user id {} ", id);
        return friendMapper.toListFriendDTO(friends);
    }

    @Override
    public List<FriendDTO> getFriendsByUserIdAndRecipientName(long id, String recipientName) {
        log.debug("Fetching all friends with user id {} and recipient name {} ", id, recipientName);
        List<Friend> friends = friendRepository.findFriendsByUser_IdAndFriend_Name(id,recipientName);
        log.info("Fetched all friends of user id {} and recipient name {} ", id, recipientName);
        return friendMapper.toListFriendDTO(friends);
    }

    @Override
    public FriendDTO updateFriend(FriendDTO friendDTO) {
        log.debug("Attempting to update friend with id {} ", friendDTO.getId());
        Friend friend = friendRepository.findById(friendDTO.getId()).orElseThrow(
                ()->{
                    log.warn("Friend with id {} not found", friendDTO.getId());
                    return new FriendNotFoundException("Friend with id " + friendDTO.getId() + " not found");
                }
        );
        friend.setStatus(friendDTO.getStatus());
        friendRepository.save(friend);
        return friendMapper.toFriendDTO(friend);
    }

    @Override
    public FriendDTO createFriend(FriendDTO friendDTO) {
        log.debug("Attempting to create friend with id {} ", friendDTO.getId());
        User user = userRepository.findById(friendDTO.getUserId()).orElseThrow(
                ()->{
                    log.warn("Friend with id {} not found", friendDTO.getId());
                    return new UserNotFound("Friend with id " + friendDTO.getId() + " not found");
                }
        );
        User friend = userRepository.findById(friendDTO.getFriendId()).orElseThrow(
                ()-> {
                    log.warn("Friend with id {} not found", friendDTO.getFriendId());
                    return new UserNotFound("Friend with id " + friendDTO.getFriendId() + " not found");
                }
        );

        Friend RelationshipFromUser = friendMapper.toFriend(friendDTO,user,friend);
        Friend RelationshipToFriend = friendMapper.toFriend(friendDTO,friend,user);

        user.addFriend(RelationshipFromUser);

        friendRepository.save(RelationshipFromUser);
        friendRepository.save(RelationshipToFriend);

        return friendMapper.toFriendDTO(RelationshipFromUser);
    }

    @Override
    public void deleteFriend(long id) {
        Friend friend = friendRepository.findById(id).orElseThrow(
                ()->{
                    log.warn("Friend with id {} not found", id);
                    return new FriendNotFoundException("Friend with id " + id + " not found");
                }
        );
        friend.getUser().removeFriend(friend);
        friendRepository.delete(friend);
    }
}
