package hu.hm.fitjourneyapi.controller.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.controller.fitness.WorkoutController;
import hu.hm.fitjourneyapi.dto.social.comment.CommentCreateDTO;
import hu.hm.fitjourneyapi.dto.social.comment.CommentDTO;
import hu.hm.fitjourneyapi.services.interfaces.social.CommentService;
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
public class CommentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getComment_Success_ReturnsDTO() throws Exception {
        UUID commentId = UUID.randomUUID();
        CommentDTO mockDto = new CommentDTO();
        mockDto.setId(commentId);
        mockDto.setContent("Great workout!");

        when(commentService.getCommentById(commentId)).thenReturn(mockDto);

        mockMvc.perform(get("/api/comment/{id}", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Great workout!"));
    }

    @Test
    @WithMockUser
    void getCommentsByPostAndUser_Success_ReturnsList() throws Exception {
        UUID postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        when(commentService.getCommentsByPostIdAndUserId(postId, userId))
                .thenReturn(List.of(new CommentDTO()));

        mockMvc.perform(get("/api/comment/post/{postId}/user/{userId}", postId, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser
    void postComment_Success_Returns200() throws Exception {
        UUID postId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        CommentCreateDTO createDTO = new CommentCreateDTO();
        createDTO.setContent("Keep it up!");

        when(commentService.createComment(any(CommentCreateDTO.class), eq(postId), eq(userId)))
                .thenReturn(new CommentDTO());

        mockMvc.perform(post("/api/comment/post/{postId}/user/{userId}", postId, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateComment_Success_ReturnsUpdatedDTO() throws Exception {
        UUID commentId = UUID.randomUUID();
        String newContent = "Updated content";

        when(commentService.updateComment(eq(commentId), anyString())).thenReturn(new CommentDTO());

        mockMvc.perform(put("/api/comment/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newContent))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteComment_Failure_Returns400() throws Exception {
        UUID commentId = UUID.randomUUID();
        doThrow(new RuntimeException("Delete failed")).when(commentService).deleteComment(commentId);

        mockMvc.perform(delete("/api/comment/{id}", commentId))
                .andExpect(status().isBadRequest());
    }
}