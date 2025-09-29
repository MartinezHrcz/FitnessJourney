package hu.hm.fitjourneyapi.repository.social;

import hu.hm.fitjourneyapi.model.social.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}