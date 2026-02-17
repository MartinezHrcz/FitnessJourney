package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.controller.social.MessageController;
import hu.hm.fitjourneyapi.dto.fitness.exerciseTemplates.DefaultExerciseDTO;
import hu.hm.fitjourneyapi.services.interfaces.fitness.DefaultExerciseService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DefaultExerciseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DefaultExerciseService defaultExerciseService;

    @Test
    @WithMockUser
    void getDefaultExercises_ShouldReturnList() throws Exception {
        DefaultExerciseDTO benchPress = new DefaultExerciseDTO();
        benchPress.setName("Bench Press");

        when(defaultExerciseService.getDefaultExercises()).thenReturn(List.of(benchPress));

        mockMvc.perform(get("/api/default/exercise"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Bench Press"));
    }

    @Test
    @WithMockUser
    void getDefaultExerciseById_ShouldReturnExercise() throws Exception {
        UUID id = UUID.randomUUID();
        DefaultExerciseDTO dto = new DefaultExerciseDTO();
        dto.setId(id);
        dto.setName("Squat");

        when(defaultExerciseService.getDefaultExercise(id)).thenReturn(dto);

        mockMvc.perform(get("/api/default/exercise/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Squat"));
    }

    @Test
    @WithMockUser
    void getDefaultExercisesByName_ShouldReturnFilteredList() throws Exception {
        String searchName = "Push";
        DefaultExerciseDTO pushUp = new DefaultExerciseDTO();
        pushUp.setName("Push Up");

        when(defaultExerciseService.getDefaultExercisesByName(searchName)).thenReturn(List.of(pushUp));

        mockMvc.perform(get("/api/default/exercise/byname/{name}", searchName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Push Up"));
    }
}