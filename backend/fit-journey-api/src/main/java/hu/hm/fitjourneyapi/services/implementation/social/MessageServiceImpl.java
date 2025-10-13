package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.exception.social.message.MessageNotFoundException;
import hu.hm.fitjourneyapi.mapper.social.MessageMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Message;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.MessageRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<MessageDTO> getMessages() {
        log.debug("Getting all messages");
        List<Message> messages = messageRepository.findAll();
        List<MessageDTO> dtos = messageMapper.toDTO(messages);
        log.info("Got {} messages", messages.size());
        return dtos;
    }

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

    @Override
    public List<MessageDTO> getMessagesBySenderId(long id) {
        log.debug("Getting messages by sender id {}", id);
        List<Message> messages = messageRepository.findAllBySender_Id(id);
        List<MessageDTO> dtos = messageMapper.toDTO(messages);
        log.info("Got {} messages by sender id {}", messages.size(), id);
        return dtos;
    }

    @Override
    public List<MessageDTO> getMessagesByRecipientId(long id) {
        log.debug("Getting messages by recipient id {}", id);
        List<Message> messages = messageRepository.findAllByRecipient_Id(id);
        List<MessageDTO> dtos = messageMapper.toDTO(messages);
        log.info("Got {} messages by recipient id {}", messages.size(), id);
        return dtos;
    }

    @Override
    public MessageDTO createMessage(MessageDTO messageDTO) {
        log.debug("Attempting to creat message");
        User sender = userRepository.findById(messageDTO.getSenderId()).orElseThrow(
                ()->{
                    log.warn("User {} not found", messageDTO.getSenderId());
                    return new MessageNotFoundException("User " + messageDTO.getSenderId() + " not found");
                }
        );
        User recipient = userRepository.findById(messageDTO.getRecipientId()).orElseThrow(
                ()-> {
                    log.warn("User {} not found", messageDTO.getRecipientId());
                    return new MessageNotFoundException("User " + messageDTO.getRecipientId() + " not found");
                }
        );
        Message message = messageMapper.toMessage(messageDTO, sender, recipient);
        log.info("Created message with id {} ", message.getId());
        return messageMapper.toDTO(message);
    }

    @Override
    public MessageDTO updateMessage(MessageDTO messageDTO) {
        return null;
    }

    @Override
    public void deleteMessage(long id) {

    }
}
