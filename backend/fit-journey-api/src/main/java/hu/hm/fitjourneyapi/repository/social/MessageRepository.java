package hu.hm.fitjourneyapi.repository.social;

import hu.hm.fitjourneyapi.model.social.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySender_Id(UUID senderId);

    List<Message> findAllByRecipient_Id(UUID recipientId);

    List<Message> findAllBySender_IdAndRecipient_Id(UUID senderId, UUID recipientId);
}