package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.exception.social.message.MessageNotFoundException;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.social.MessageMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Message;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.MessageRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper messageMapper, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageDTO> getMessages() {
        log.debug("Getting all messages");
        List<Message> messages = messageRepository.findAll();
        List<MessageDTO> dtos = messageMapper.toDTO(messages);
        log.info("Got {} messages", messages.size());
        return dtos;
    }

    @Transactional(readOnly = true)
    @Override
    public MessageDTO getMessageById(long id) {
        log.debug("Getting message with id {}", id);
        Message message = messageRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("Message with id {} not found", id);
                    return new MessageNotFoundException("Message with id " + id + " not found");
                }
        );
        log.info("Got message {}", message);
        return messageMapper.toDTO(message);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageDTO> getMessagesBySenderId(UUID id) {
        log.debug("Getting messages by sender id {}", id);
        List<Message> messages = messageRepository.findAllBySender_Id(id);
        List<MessageDTO> dtos = messageMapper.toDTO(messages);
        log.info("Got {} messages by sender id {}", messages.size(), id);
        return dtos;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MessageDTO> getMessagesBySenderAndRecipientId(UUID senderId, UUID recipientId) {
        log.debug("Getting messages by sender id {} and recipient id {}", senderId, recipientId);
        List<Message> messages = messageRepository.findAllBySender_IdAndRecipient_Id(senderId,recipientId);
        List<MessageDTO> dtos = messageMapper.toDTO(messages);
        log.info("Got {} messages by sender and recipient id", messages.size());
        return dtos;
    }

    @Transactional
    @Override
    public MessageDTO createMessage(MessageDTO messageDTO) {
        log.debug("Attempting to creat message");
        User sender = userRepository.findById(messageDTO.getSenderId()).orElseThrow(
                ()->{
                    log.warn("User {} not found", messageDTO.getSenderId());
                    return new UserNotFound("User " + messageDTO.getSenderId() + " not found");
                }
        );
        User recipient = userRepository.findById(messageDTO.getRecipientId()).orElseThrow(
                ()-> {
                    log.warn("User {} not found", messageDTO.getRecipientId());
                    return new UserNotFound("User " + messageDTO.getRecipientId() + " not found");
                }
        );
        Message message = messageMapper.toMessage(messageDTO, sender, recipient);
        message = messageRepository.save(message);
        log.info("Created message with id {} ", message.getId());
        return messageMapper.toDTO(message);
    }

    @Transactional
    @Override
    public MessageDTO updateMessage(long id, MessageDTO messageDTO) {
        log.debug("Attempting to update message");
        Message message = messageRepository.findById(id).orElseThrow(
                ()-> {
                    log.warn("Message with id {} not found", id);
                    return new MessageNotFoundException("Message " + id + " not found");
                }
        );
        message.setContent(messageDTO.getContent());
        log.info("Updated message with id {} ", id);
        return messageMapper.toDTO(message);
    }

    @Transactional
    @Override
    public void deleteMessage(long id) {
        log.debug("Attempting to delete message");
        Message message = messageRepository.findById(id).orElseThrow(
                ()->{
                    log.warn("Message with id {} not found", id);
                    return new MessageNotFoundException("Message " + id + " not found");
                }
        );
        log.info("Deleted message with id {} ", id);
        messageRepository.delete(message);
    }
}
