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
import hu.hm.fitjourneyapi.services.interfaces.social.MessageService;
import hu.hm.fitjourneyapi.utils.MessageTestFactory;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MessageServiceTests {

    @Autowired
    private MessageService messageService;

    @MockitoBean
    private MessageRepository messageRepository;

    @MockitoBean
    private MessageMapper messageMapper;

    @MockitoBean
    private UserRepository userRepository;

    private Message message;
    private MessageDTO messageDTO;
    private CreateMessageDTO createMessageDTO;
    private User sender;
    private User recipient;

    @BeforeEach
    void setUp() {
        sender = UserTestFactory.getUser();
        sender.setId(UUID.randomUUID());
        recipient = UserTestFactory.getUser();
        recipient.setId(UUID.randomUUID());
        message = MessageTestFactory.getMessage(sender, recipient);
        messageDTO = MessageTestFactory.getMessageDTO();
        createMessageDTO = MessageTestFactory.getCreateMessageDTO();
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(messageRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(message));
        when(messageRepository.findAllBySender_Id(any(UUID.class))).thenReturn(List.of(message));
        when(messageRepository.findAllBySender_IdAndRecipient_Id(any(UUID.class), any(UUID.class))).thenReturn(List.of(message));
        when(messageRepository.findAll()).thenReturn(List.of(message));
        when(messageMapper.toDTO(List.of(message))).thenReturn(List.of(messageDTO));
        when(userRepository.findById(eq(sender.getId()))).thenReturn(Optional.of(sender));
        when(userRepository.findById(eq(recipient.getId()))).thenReturn(Optional.of(recipient));

        when(messageMapper.toMessage(any(MessageDTO.class), any(User.class), any(User.class))).thenReturn(message);
        when(messageMapper.toDTO(any(Message.class))).thenAnswer(
                invocation -> {
                    Message message = invocation.getArgument(0);
                    return MessageDTO.builder()
                            .id(message.getId())
                            .content(message.getContent())
                            .recipientId(message.getRecipient().getId())
                            .senderId(message.getSender().getId())
                            .build();
                }
        );
    }

    @Test
    public void GetMessagesTest_GetAll_success() {
        List<MessageDTO> result = messageService.getMessages();
        assertNotNull(result);
        assertTrue(!result.isEmpty());
        assertEquals(messageDTO.getContent(), result.getFirst().getContent());
        assertEquals(messageDTO.getRecipientId(), result.getFirst().getRecipientId());
        assertEquals(messageDTO.getSenderId(), result.getFirst().getSenderId());
    }

    @Test
    public void GetMessageByIdTest_Get_success() {
        MessageDTO result = messageService.getMessageById(UUID.randomUUID());
        assertNotNull(result);
        assertEquals(messageDTO.getContent(), result.getContent());
    }

    @Test
    public void GetMessageByIdTest_MessageNotFound_fail() {
        when(messageRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(MessageNotFoundException.class, () -> messageService.getMessageById(UUID.randomUUID()));
    }

    @Test
    public void GetMessageBySenderIDTest_Get_success() {
        List<MessageDTO> result = messageService.getMessagesBySenderId(sender.getId());
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(messageDTO.getContent(), result.getFirst().getContent());
        assertEquals(messageDTO.getRecipientId(), result.getFirst().getRecipientId());
        assertEquals(messageDTO.getSenderId(), result.getFirst().getSenderId());
    }

    @Test
    public void GetMessagesBySenderAndRecipientIDTest_Get_success() {
        List<MessageDTO> result = messageService.getMessagesBySenderAndRecipientId(sender.getId(), recipient.getId());
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(messageDTO.getContent(), result.getFirst().getContent());
        assertEquals(messageDTO.getRecipientId(), result.getFirst().getRecipientId());
        assertEquals(messageDTO.getSenderId(), result.getFirst().getSenderId());
    }


    public void CreateMessageTest_Create_success() {

        MessageDTO result = messageService.createMessage(createMessageDTO);
        assertNotNull(result);
        assertEquals(messageDTO.getContent(), result.getContent());
    }

    @Test
    public void CreateMessageTest_UserNotFound_fail() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(UserNotFound.class, () -> messageService.createMessage(createMessageDTO));
    }

    @Test
    public void UpdateMessageTest_Update_success() {
        UpdateMessageDTO updateDTO = UpdateMessageDTO.builder()
                .content("update content")
                .build();
        MessageDTO result = messageService.updateMessage(messageDTO.getId(), updateDTO);
        assertNotEquals(messageDTO.getContent(), result.getContent());
    }

    @Test
    public void UpdateMessageTest_MessageNotFound_fail() {
        when(messageRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(MessageNotFoundException.class, () -> messageService.updateMessage(messageDTO.getId(), new UpdateMessageDTO("")));
    }

    @Test
    public void DeleteMessageTest_Delete_success() {
        messageService.deleteMessage(UUID.randomUUID());
        verify(messageRepository, times(1)).delete(any(Message.class));
    }

    @Test
    public void DeleteMessageTest_MessageNotFound_fail() {
        when(messageRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> messageService.deleteMessage(UUID.fromString("e")));
    }

}
