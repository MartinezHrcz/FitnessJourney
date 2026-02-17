package hu.hm.fitjourneyapi.controller.fitness;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.controller.social.MessageController;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.services.interfaces.fitness.WorkoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkoutControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkoutService workoutService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void createWorkout_Success_ReturnsId() throws Exception {
        UUID workoutId = UUID.randomUUID();
        WorkoutCreateDTO createDTO = new WorkoutCreateDTO();

        when(workoutService.createWorkout(any(WorkoutCreateDTO.class))).thenReturn(workoutId);

        mockMvc.perform(post("/api/workout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + workoutId + "\""));
    }

    @Test
    @WithMockUser
    void createWorkout_UserNotFound_Returns404() throws Exception {
        when(workoutService.createWorkout(any())).thenThrow(new UserNotFound("User not found"));

        mockMvc.perform(post("/api/workout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new WorkoutCreateDTO())))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getWorkout_Success_ReturnsDTO() throws Exception {
        UUID workoutId = UUID.randomUUID();
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setId(workoutId);
        workoutDTO.setName("Morning Routine");

        when(workoutService.getWorkoutByWorkoutId(workoutId)).thenReturn(workoutDTO);

        mockMvc.perform(get("/api/workout/{id}", workoutId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(workoutId.toString()))
                .andExpect(jsonPath("$.name").value("Morning Routine"));
    }

    @Test
    @WithMockUser
    void addExerciseToWorkout_Success_ReturnsUpdatedWorkout() throws Exception {
        UUID workoutId = UUID.randomUUID();
        UUID exerciseId = UUID.randomUUID();
        WorkoutDTO updatedWorkout = new WorkoutDTO();
        updatedWorkout.setId(workoutId);

        when(workoutService.addExerciseToWorkout(workoutId, exerciseId)).thenReturn(updatedWorkout);

        mockMvc.perform(put("/api/workout/addexc/{id}/{exerciseId}", workoutId, exerciseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(workoutId.toString()));
    }

    @Test
    @WithMockUser
    void deleteWorkout_Success_ReturnsMessage() throws Exception {
        UUID workoutId = UUID.randomUUID();

        doNothing().when(workoutService).deleteWorkoutById(workoutId);

        mockMvc.perform(delete("/api/workout/{id}", workoutId))
                .andExpect(status().isOk())
                .andExpect(content().string("Workout with id " + workoutId + " deleted"));
    }

    @Test
    @WithMockUser
    void deleteWorkout_NotFound_Returns404() throws Exception {
        UUID workoutId = UUID.randomUUID();
        doThrow(new WorkoutNotFound("Not found")).when(workoutService).deleteWorkoutById(workoutId);

        mockMvc.perform(delete("/api/workout/{id}", workoutId))
                .andExpect(status().isNotFound());
    }
}