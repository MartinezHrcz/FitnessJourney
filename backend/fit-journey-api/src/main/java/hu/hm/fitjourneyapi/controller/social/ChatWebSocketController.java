package hu.hm.fitjourneyapi.controller.social;

import hu.hm.fitjourneyapi.dto.social.message.CreateMessageDTO;
import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.services.interfaces.social.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;

    public ChatWebSocketController(SimpMessagingTemplate template, MessageService messageService) {
        this.template = template;
        this.messageService = messageService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload CreateMessageDTO message) {
        MessageDTO messageDTO = messageService.createMessage(message);

        template.convertAndSendToUser(
            message.getRecipientId().toString(), "/queue/message", messageDTO
        );
    }
}
