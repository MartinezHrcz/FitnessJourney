package hu.hm.fitjourneyapi.services.interfaces.social;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    List<MessageDTO> getMessages();

    MessageDTO getMessageById(long id);

    List<MessageDTO> getMessagesBySenderId(UUID id);

    List<MessageDTO> getMessagesBySenderAndRecipientId(UUID senderId, UUID recipientId);

    MessageDTO createMessage(MessageDTO messageDTO);

    MessageDTO updateMessage(long id,MessageDTO messageDTO);

    void deleteMessage(long id);
}
