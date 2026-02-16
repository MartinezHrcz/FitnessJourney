package hu.hm.fitjourneyapi.controller.fitness;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.dto.fitness.excercise.AbstractExerciseDTO;
import hu.hm.fitjourneyapi.dto.fitness.excercise.ExerciseUpdateDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.services.interfaces.fitness.ExerciseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExerciseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExerciseService exerciseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getExercise_NotFound_Returns404() throws Exception {
        UUID id = UUID.randomUUID();
        when(exerciseService.getById(id)).thenThrow(new ExerciseNotFound("Not found"));

        mockMvc.perform(get("/api/exercise/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void updateExercise_Success() throws Exception {
        UUID id = UUID.randomUUID();
        ExerciseUpdateDTO updateDTO = ExerciseUpdateDTO.builder().build();
        updateDTO.setName("New Name");

        mockMvc.perform(put("/api/exercise/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void addSet_Success() throws Exception {
        UUID id = UUID.randomUUID();
        StrengthSetDTO setDTO = new StrengthSetDTO();
        setDTO.setReps(12);
        setDTO.setWeight(60);

        mockMvc.perform(put("/api/exercise/addset/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(setDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteExercise_Success_ReturnsNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/exercise/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void removeSet_Success() throws Exception {
        UUID id = UUID.randomUUID();
        long setId = 1L;

        mockMvc.perform(delete("/api/exercise/removeset/{id}/{setId}", id, setId))
                .andExpect(status().isOk());
    }
}