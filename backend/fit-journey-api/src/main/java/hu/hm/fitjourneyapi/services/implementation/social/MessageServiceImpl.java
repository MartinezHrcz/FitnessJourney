package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.mapper.social.MessageMapper;
import hu.hm.fitjourneyapi.model.social.Message;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.MessageRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper mapper;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, MessageMapper mapper, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<MessageDTO> getMessages() {
        log.debug("Getting all messages");
        List<Message> messages = messageRepository.findAll();
        List<MessageDTO> dtos = mapper.toDTO(messages);
        log.info("Got {} messages", messages.size());
        return dtos;
    }

    @Override
    public MessageDTO getMessageById(long id) {
        return null;
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
