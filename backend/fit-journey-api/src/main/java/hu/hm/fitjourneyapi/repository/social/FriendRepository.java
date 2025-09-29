package hu.hm.fitjourneyapi.repository.social;

import hu.hm.fitjourneyapi.model.social.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}