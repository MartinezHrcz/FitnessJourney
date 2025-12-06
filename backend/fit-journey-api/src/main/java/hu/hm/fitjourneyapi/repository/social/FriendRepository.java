package hu.hm.fitjourneyapi.repository.social;

import hu.hm.fitjourneyapi.model.social.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
    List<Friend> findFriendsByUser_Id(UUID userId);

    List<Friend> findFriendsByUser_IdAndFriend_Name(UUID userId, String friendName);
}