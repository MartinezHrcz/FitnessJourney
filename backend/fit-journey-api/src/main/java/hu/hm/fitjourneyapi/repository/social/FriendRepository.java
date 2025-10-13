package hu.hm.fitjourneyapi.repository.social;

import hu.hm.fitjourneyapi.model.social.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findFriendsByUser_Id(long userId);

    List<Friend> findFriendsByUser_IdAndFriend_Name(long userId, String friendName);
}