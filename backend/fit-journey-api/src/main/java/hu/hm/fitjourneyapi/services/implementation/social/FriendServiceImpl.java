package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.mapper.social.FriendMapper;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.FriendRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.FriendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        return null;
    }

    @Override
    public List<FriendDTO> getFriends() {
        return List.of();
    }

    @Override
    public List<FriendDTO> getFriendsByUserId(long id) {
        return List.of();
    }

    @Override
    public List<FriendDTO> getFriendsByUserIdAndRecipientName(long id, String recipientName) {
        return List.of();
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
