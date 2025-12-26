package hu.hm.fitjourneyapi.controller.social;

import hu.hm.fitjourneyapi.dto.social.message.CreateMessageDTO;
import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.dto.social.message.UpdateMessageDTO;
import hu.hm.fitjourneyapi.model.social.Message;
import hu.hm.fitjourneyapi.services.interfaces.social.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        return ResponseEntity.ok(messageService.getMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessage(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(messageService.getMessageById(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bysender/{id}")
    public ResponseEntity<List<MessageDTO>> getMessagesBySender(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(messageService.getMessagesBySenderId(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByRecipient(@PathVariable UUID senderId, @PathVariable UUID recipientId) {
        try {
            return ResponseEntity.ok(messageService.getMessagesBySenderAndRecipientId(senderId, recipientId));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@RequestBody CreateMessageDTO messageDTO) {
        try{
            return ResponseEntity.ok(messageService.createMessage(messageDTO));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable UUID id, @RequestBody UpdateMessageDTO messageDTO) {
        try{
            return ResponseEntity.ok(messageService.updateMessage(id, messageDTO));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageDTO> deleteMessage(@PathVariable UUID id) {
        try {
            messageService.deleteMessage(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
