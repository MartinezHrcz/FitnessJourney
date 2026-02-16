package hu.hm.fitjourneyapi.controller.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.dto.social.friend.FriendCreateDTO;
import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import hu.hm.fitjourneyapi.services.interfaces.social.FriendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FriendControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FriendService friendService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getFriend_Success_ReturnsDTO() throws Exception {
        UUID friendRequestId = UUID.randomUUID();
        FriendDTO mockDto = new FriendDTO();
        mockDto.setId(friendRequestId);

        when(friendService.getFriendById(friendRequestId)).thenReturn(mockDto);

        mockMvc.perform(get("/api/friend/{id}", friendRequestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(friendRequestId.toString()));
    }

    @Test
    @WithMockUser
    void getFriend_NotFound_Returns404() throws Exception {
        UUID friendRequestId = UUID.randomUUID();
        when(friendService.getFriendById(friendRequestId)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/api/friend/{id}", friendRequestId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void createFriend_Success_Returns200() throws Exception {
        FriendCreateDTO createDTO = new FriendCreateDTO();
        FriendDTO responseDTO = new FriendDTO();

        when(friendService.createFriend(any(FriendCreateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/friend")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void acceptFriend_Success_Returns200() throws Exception {
        UUID friendRequestId = UUID.randomUUID();

        mockMvc.perform(put("/api/friend/{id}/accept", friendRequestId))
                .andExpect(status().isOk());

        verify(friendService, times(1)).updateFriend(friendRequestId, FriendStatus.ACCEPTED);
    }

    @Test
    @WithMockUser
    void deleteFriend_Success_Returns204() throws Exception {
        UUID friendRequestId = UUID.randomUUID();

        mockMvc.perform(delete("/api/friend/{id}", friendRequestId))
                .andExpect(status().isNoContent());

        verify(friendService).deleteFriend(friendRequestId);
    }

    @Test
    @WithMockUser
    void deleteFriend_Error_Returns400() throws Exception {
        UUID friendRequestId = UUID.randomUUID();
        doThrow(new RuntimeException("DB Error")).when(friendService).deleteFriend(friendRequestId);

        mockMvc.perform(delete("/api/friend/{id}", friendRequestId))
                .andExpect(status().isBadRequest());
    }
}