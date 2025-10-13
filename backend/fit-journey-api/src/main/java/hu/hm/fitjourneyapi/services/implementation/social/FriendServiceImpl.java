package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.exception.social.friend.FriendNotFoundException;
import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.mapper.social.FriendMapper;
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
        List<Friend> friends = friendRepository.findFriendsByUser_Id(id).stream()
                .filter(friend ->  friend.getFriend().getName().equals(recipientName)).toList();
        log.info("Fetched all friends of user id {} and recipient name {} ", id, recipientName);
        return friendMapper.toListFriendDTO(friends);
    }

    @Override
    public FriendDTO updateFriend(FriendDTO friendDTO) {
        return null;
    }

    @Override
    public FriendDTO createFriend(FriendDTO friendDTO) {
        return null;
    }

    @Override
    public void deleteFriend(long id) {

    }
}
