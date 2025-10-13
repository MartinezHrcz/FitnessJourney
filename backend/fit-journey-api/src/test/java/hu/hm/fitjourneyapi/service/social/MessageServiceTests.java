package hu.hm.fitjourneyapi.service.social;

import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.exception.social.message.MessageNotFoundException;
import hu.hm.fitjourneyapi.mapper.social.MessageMapper;
import hu.hm.fitjourneyapi.mapper.social.PostMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Message;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.MessageRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.MessageService;
import hu.hm.fitjourneyapi.utils.MessageTestFactory;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    private UserDTO userDTO;
    private User sender;
    private User recipient;

    @BeforeEach
    void setUp() {
        sender = UserTestFactory.getUser();
        sender.setId(1);
        recipient = UserTestFactory.getUser();
        recipient.setId(2);
        message = MessageTestFactory.getMessage(sender, recipient);
        messageDTO = MessageTestFactory.getMessageDTO();
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(messageRepository.findById(any(long.class))).thenReturn(Optional.ofNullable(message));
        when(messageRepository.findAllBySender_Id(any(long.class))).thenReturn(List.of(message));
        when(messageRepository.findAllBySender_IdAndRecipient_Id(any(long.class), any(long.class))).thenReturn(List.of(message));
        when(messageRepository.findAll()).thenReturn(List.of(message));
        when(messageMapper.toDTO(List.of(message))).thenReturn(List.of(messageDTO));
        when(messageMapper.toDTO(any(Message.class))).thenAnswer(
                invocation-> {
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
        MessageDTO result = messageService.getMessageById(1L);
        assertNotNull(result);
        assertEquals(messageDTO.getContent(), result.getContent());
        assertEquals(messageDTO.getRecipientId(), result.getRecipientId());
        assertEquals(messageDTO.getSenderId(), result.getSenderId());
    }

    @Test
    public void GetMessageByIdTest_MessageNotFound_fail() {
        when(messageRepository.findById(any(long.class))).thenReturn(Optional.empty());
        assertThrows(MessageNotFoundException.class, ()->messageService.getMessageById(1L));
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

    }

    public void CreateMessageTest_UserNotFound_fail() {

    }

    public void UpdateMessageTest_Update_success() {

    }

    public void UpdateMessageTest_MessageNotFound_fail() {

    }

    public void DeleteMessageTest_Delete_success() {

    }

    public void DeleteMessageTest_MessageNotFound_fail() {

    }



}
