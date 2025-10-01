package hu.hm.fitjourneyapi.services.interfaces;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;

import java.util.List;

public interface MessageService {
    List<MessageDTO> getMessages();

    MessageDTO getMessageById(long id);

    List<MessageDTO> getMessagesBySenderId(long id);

    List<MessageDTO> getMessagesByRecipientId(long id);

    MessageDTO createMessage(MessageDTO messageDTO);

    MessageDTO updateMessage(MessageDTO messageDTO);

    void deleteMessage(long id);
}
