package hu.hm.fitjourneyapi.controller.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import hu.hm.fitjourneyapi.services.interfaces.social.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String MOCK_USER_ID = "99371d57-e1f7-4f17-8d30-e6406daad176";

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void getById_Success_ReturnsPost() throws Exception {
        UUID postId = UUID.randomUUID();
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postId);
        postDTO.setContent("Workout update!");

        when(postService.getPostById(eq(postId), eq(UUID.fromString(MOCK_USER_ID))))
                .thenReturn(postDTO);

        mockMvc.perform(get("/api/post/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Workout update!"));
    }

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void createWithImage_Success_ReturnsPost() throws Exception {
        PostCreateDTO createDTO = PostCreateDTO.builder().content("Check out my gym progress!").build();

        MockMultipartFile contentPart = new MockMultipartFile(
                "content",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsBytes(createDTO)
        );

        // Mocking the image file part
        MockMultipartFile imagePart = new MockMultipartFile(
                "image",
                "gym.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake-image-data".getBytes()
        );

        when(postService.createPostWithImage(any(), any(), any())).thenReturn(new PostDTO());

        mockMvc.perform(multipart("/api/post/with-image")
                        .file(contentPart)
                        .file(imagePart))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void likePost_Success_ReturnsOk() throws Exception {
        UUID postId = UUID.randomUUID();

        mockMvc.perform(post("/api/post/{id}/like", postId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void delete_Success_ReturnsNoContent() throws Exception {
        UUID postId = UUID.randomUUID();

        mockMvc.perform(delete("/api/post/{id}", postId))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void update_BadRequest_OnException() throws Exception {
        UUID postId = UUID.randomUUID();
        PostUpdateDTO updateDTO = new PostUpdateDTO();

        when(postService.updatePost(any(), any(), any())).thenThrow(new RuntimeException("Unauthorized"));

        mockMvc.perform(put("/api/post/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest());
    }
}