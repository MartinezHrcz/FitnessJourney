package hu.hm.fitjourneyapi.controller.fitness;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserExerciseUpdateDto;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.UserMadeExercisesDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.services.interfaces.fitness.UserExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class UserMadeExerciseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserExerciseService userExerciseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getByUser_Success_ReturnsList() throws Exception {
        UUID userId = UUID.randomUUID();
        UserMadeExercisesDTO customSquat = new UserMadeExercisesDTO();
        customSquat.setName("My Custom Squat");

        when(userExerciseService.getUserMadeExercisesByUser(userId))
                .thenReturn(List.of(customSquat));

        mockMvc.perform(get("/api/user/templates/byuser/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("My Custom Squat"));
    }

    @Test
    @WithMockUser
    void createUserTemplate_Success_ReturnsDTO() throws Exception {
        UUID userId = UUID.randomUUID();
        UserExerciseUpdateDto updateDto = new UserExerciseUpdateDto();
        updateDto.setName("New Template");

        UserMadeExercisesDTO responseDto = new UserMadeExercisesDTO();
        responseDto.setName("New Template");

        when(userExerciseService.createUserMadeExercise(eq(userId), any(UserExerciseUpdateDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/api/user/templates/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Template"));
    }

    @Test
    @WithMockUser
    void getById_NotFound_Returns404() throws Exception {
        UUID templateId = UUID.randomUUID();
        when(userExerciseService.getUserMadeExercise(templateId))
                .thenThrow(new ExerciseNotFound("Template not found"));

        mockMvc.perform(get("/api/user/templates/{id}", templateId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteUserTemplate_Success() throws Exception {
        UUID templateId = UUID.randomUUID();

        mockMvc.perform(delete("/api/user/templates/{id}", templateId))
                .andExpect(status().isOk())
                .andExpect(content().string("Exercise template deleted"));
    }
}