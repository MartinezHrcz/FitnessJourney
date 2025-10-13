package hu.hm.fitjourneyapi.repository.social;

import hu.hm.fitjourneyapi.model.social.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySender_Id(long senderId);

    List<Message> findAllByRecipient_Id(long recipientId);
}