package hu.hm.fitjourneyapi.controller.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.dto.social.message.CreateMessageDTO;
import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.dto.social.message.UpdateMessageDTO;
import hu.hm.fitjourneyapi.services.interfaces.social.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getMessage_Success_ReturnsDTO() throws Exception {
        UUID messageId = UUID.randomUUID();
        MessageDTO mockDto = new MessageDTO();
        mockDto.setId(messageId);
        mockDto.setContent("Hello, ready for the workout?");

        when(messageService.getMessageById(messageId)).thenReturn(mockDto);

        mockMvc.perform(get("/api/message/{id}", messageId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, ready for the workout?"));
    }

    @Test
    @WithMockUser
    void getMessagesBySenderAndRecipient_Success() throws Exception {
        UUID senderId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();

        when(messageService.getMessagesBySenderAndRecipientId(senderId, recipientId))
                .thenReturn(List.of(new MessageDTO()));

        mockMvc.perform(get("/api/message/{senderId}/{recipientId}", senderId, recipientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser
    void createMessage_Success_Returns200() throws Exception {
        CreateMessageDTO createDTO = CreateMessageDTO.builder().content("New message").build();


        when(messageService.createMessage(any(CreateMessageDTO.class))).thenReturn(new MessageDTO());

        mockMvc.perform(post("/api/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateMessage_Success_ReturnsUpdatedDTO() throws Exception {
        UUID messageId = UUID.randomUUID();
        UpdateMessageDTO updateDTO = UpdateMessageDTO.builder().content("Corrected message content").build();

        when(messageService.updateMessage(eq(messageId), any(UpdateMessageDTO.class)))
                .thenReturn(new MessageDTO());

        mockMvc.perform(put("/api/message/{id}", messageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteMessage_Success_Returns204() throws Exception {
        UUID messageId = UUID.randomUUID();

        mockMvc.perform(delete("/api/message/{id}", messageId))
                .andExpect(status().isNoContent());

        verify(messageService, times(1)).deleteMessage(messageId);
    }

    @Test
    @WithMockUser
    void getMessage_NotFound_Returns404() throws Exception {
        UUID messageId = UUID.randomUUID();
        when(messageService.getMessageById(messageId)).thenThrow(new RuntimeException("Message missing"));

        mockMvc.perform(get("/api/message/{id}", messageId))
                .andExpect(status().isNotFound());
    }
}