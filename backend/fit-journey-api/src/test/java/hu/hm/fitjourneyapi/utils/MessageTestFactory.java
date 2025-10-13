package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Message;

import java.time.LocalDateTime;

public class MessageTestFactory {
    public Message getMessage(User sender, User recipient){
        return Message.builder()
                .id(1L)
                .sender(sender)
                .recipient(recipient)
                .content("Test Content")
                .sentTime(LocalDateTime.now())
                .build();
    }

    public MessageDTO getMessageDTO(){
        return MessageDTO.builder()
                .senderId(1L)
                .recipientId(2L)
                .content("Test Content")
                .sentTime(LocalDateTime.now())
                .build();
    }
}
