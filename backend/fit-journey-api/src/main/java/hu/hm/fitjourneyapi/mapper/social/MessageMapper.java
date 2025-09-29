package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Message;

public class MessageMapper {

    public static MessageDTO toDTO(Message message) {
        if (message == null) return null;
        return MessageDTO
                .builder()
                .recipientId(message.getRecipient().getId())
                .senderId(message.getSender().getId())
                .content(message.getContent())
                .sentTime(message.getId())
                .build();
    }

    public static Message toMessage(MessageDTO messageDTO,User sender, User recipient) {
        if (messageDTO == null) return null;
        return Message
                .builder()
                .sender(sender)
                .recipient(recipient)
                .content(messageDTO.getContent())
                .build();
    }
}
