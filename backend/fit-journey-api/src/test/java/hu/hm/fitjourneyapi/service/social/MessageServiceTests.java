package hu.hm.fitjourneyapi.service.social;

import hu.hm.fitjourneyapi.dto.social.message.CreateMessageDTO;
import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.dto.social.message.UpdateMessageDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.exception.social.message.MessageNotFoundException;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.social.MessageMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Message;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.MessageRepository;
import hu.hm.fitjourneyapi.services.implementation.social.MessageServiceImpl;
import hu.hm.fitjourneyapi.services.interfaces.social.MessageService;
import hu.hm.fitjourneyapi.utils.MessageTestFactory;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MessageServiceTests {
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageMapper messageMapper;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    private Message message;
    private MessageDTO messageDTO;
    private User sender;
    private User recipient;

    @BeforeEach
    void setUp() {
        sender = UserTestFactory.getUser();
        sender.setId(UUID.randomUUID());

        recipient = UserTestFactory.getUser();
        recipient.setId(UUID.randomUUID());

        message = MessageTestFactory.getMessage(sender, recipient);
        message.setId(UUID.randomUUID());
        message.setSentTime(LocalDateTime.now());

        messageDTO = MessageTestFactory.getMessageDTO();
        messageDTO.setSenderId(sender.getId());
        messageDTO.setRecipientId(recipient.getId());
    }

    @Test
    void getMessages_Success() {
        when(messageRepository.findAll()).thenReturn(List.of(message));
        when(messageMapper.toDTO(anyList())).thenReturn(List.of(messageDTO));

        List<MessageDTO> result = messageService.getMessages();

        assertFalse(result.isEmpty());
        verify(messageRepository).findAll();
    }

    @Test
    void getMessageById_Success() {
        when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));
        when(messageMapper.toDTO(message)).thenReturn(messageDTO);

        MessageDTO result = messageService.getMessageById(message.getId());

        assertNotNull(result);
        assertEquals(messageDTO.getContent(), result.getContent());
    }

    @Test
    void getMessagesBySenderAndRecipientId_SuccessAndSorted() {
        Message secondMessage = MessageTestFactory.getMessage(recipient, sender);
        secondMessage.setSentTime(LocalDateTime.now().plusMinutes(5));

        List<Message> firstBatch = new ArrayList<>(List.of(message));
        List<Message> secondBatch = new ArrayList<>(List.of(secondMessage));

        when(messageRepository.findAllBySender_IdAndRecipient_Id(sender.getId(), recipient.getId()))
                .thenReturn(firstBatch);
        when(messageRepository.findAllBySender_IdAndRecipient_Id(recipient.getId(), sender.getId()))
                .thenReturn(secondBatch);
        when(messageMapper.toDTO(anyList())).thenReturn(List.of(messageDTO, messageDTO));

        List<MessageDTO> result = messageService.getMessagesBySenderAndRecipientId(sender.getId(), recipient.getId());

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(messageRepository, times(2)).findAllBySender_IdAndRecipient_Id(any(), any());
    }

    @Test
    void createMessage_Success() {
        CreateMessageDTO createDTO = new CreateMessageDTO(sender.getId(), recipient.getId(), "Hello");

        when(userRepository.findById(sender.getId())).thenReturn(Optional.of(sender));
        when(userRepository.findById(recipient.getId())).thenReturn(Optional.of(recipient));
        when(messageMapper.toMessage(eq(createDTO), eq(sender), eq(recipient))).thenReturn(message);
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(messageMapper.toDTO(message)).thenReturn(messageDTO);

        MessageDTO result = messageService.createMessage(createDTO);

        assertNotNull(result);
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void createMessage_SenderNotFound_ThrowsException() {
        CreateMessageDTO createDTO = new CreateMessageDTO(UUID.randomUUID(), recipient.getId(), "Hello");
        when(userRepository.findById(createDTO.getSenderId())).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class, () -> messageService.createMessage(createDTO));
    }

    @Test
    void updateMessage_Success() {
        UpdateMessageDTO updateDTO = new UpdateMessageDTO("Updated Content");
        when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));

        MessageDTO updatedDTO = MessageDTO.builder().content("Updated Content").build();
        when(messageMapper.toDTO(message)).thenReturn(updatedDTO);

        MessageDTO result = messageService.updateMessage(message.getId(), updateDTO);

        assertEquals("Updated Content", result.getContent());
        assertEquals("Updated Content", message.getContent());
    }

    @Test
    void deleteMessage_Success() {
        when(messageRepository.findById(message.getId())).thenReturn(Optional.of(message));

        messageService.deleteMessage(message.getId());

        verify(messageRepository).delete(message);
    }

    @Test
    void deleteMessage_NotFound_ThrowsException() {
        UUID randomId = UUID.randomUUID();
        when(messageRepository.findById(randomId)).thenReturn(Optional.empty());

        assertThrows(MessageNotFoundException.class, () -> messageService.deleteMessage(randomId));
    }
}
