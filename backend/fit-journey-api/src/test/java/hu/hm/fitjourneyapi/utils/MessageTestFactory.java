package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.social.message.CreateMessageDTO;
import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageTestFactory {
    public static Message getMessage(User sender, User recipient){
        return Message.builder()
                .id(UUID.randomUUID())
                .sender(sender)
                .recipient(recipient)
                .content("Test Content")
                .sentTime(LocalDateTime.now())
                .build();
    }

    public static MessageDTO getMessageDTO(){
        return MessageDTO.builder()
                .senderId(UUID.randomUUID())
                .recipientId(UUID.randomUUID())
                .content("Test Content")
                .sentTime(LocalDateTime.now())
                .build();
    }

    public static CreateMessageDTO getCreateMessageDTO(){
        return CreateMessageDTO.builder()
                .senderId(UUID.randomUUID())
                .recipientId(UUID.randomUUID())
                .content("Test Content")
                .build();
    }
}
