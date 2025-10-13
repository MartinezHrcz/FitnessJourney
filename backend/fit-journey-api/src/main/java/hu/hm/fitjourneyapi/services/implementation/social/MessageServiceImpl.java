package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.exception.social.message.MessageNotFoundException;
import hu.hm.fitjourneyapi.mapper.social.MessageMapper;
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
        return List.of();
    }

    @Override
    public List<MessageDTO> getMessagesByRecipientId(long id) {
        return List.of();
    }

    @Override
    public MessageDTO createMessage(MessageDTO messageDTO) {
        return null;
    }

    @Override
    public MessageDTO updateMessage(MessageDTO messageDTO) {
        return null;
    }

    @Override
    public void deleteMessage(long id) {

    }
}
