package hu.hm.fitjourneyapi.services.interfaces.social;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;

import java.util.List;

public interface MessageService {
    List<MessageDTO> getMessages();

    MessageDTO getMessageById(long id);

    List<MessageDTO> getMessagesBySenderId(long id);

    List<MessageDTO> getMessagesBySenderAndRecipientId(long senderId, long recipientId);

    MessageDTO createMessage(MessageDTO messageDTO);

    MessageDTO updateMessage(long id,MessageDTO messageDTO);

    void deleteMessage(long id);
}
