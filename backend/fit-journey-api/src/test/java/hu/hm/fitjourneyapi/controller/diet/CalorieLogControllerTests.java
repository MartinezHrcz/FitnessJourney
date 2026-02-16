package hu.hm.fitjourneyapi.controller.diet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.dto.diet.CalorieLogDTO;
import hu.hm.fitjourneyapi.dto.diet.MealEntryCreateDTO;
import hu.hm.fitjourneyapi.services.interfaces.diet.CalorieLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalorieLogController.class)
public class CalorieLogControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalorieLogService calorieLogService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String MOCK_USER_ID = "99371d57-e1f7-4f17-8d30-e6406daad176";
    private final LocalDate testDate = LocalDate.of(2024, 5, 20);

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void getDailyLog_ShouldReturnLogForDate() throws Exception {
        CalorieLogDTO mockDto = new CalorieLogDTO();

        when(calorieLogService.getDailyLog(eq(UUID.fromString(MOCK_USER_ID)), eq(testDate)))
                .thenReturn(mockDto);

        mockMvc.perform(get("/api/diet/log/{date}", "2024-05-20"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void getHistoryLog_ShouldReturnUserLogs() throws Exception {
        when(calorieLogService.getHistoryLogs(eq(UUID.fromString(MOCK_USER_ID))))
                .thenReturn(List.of(new CalorieLogDTO()));

        mockMvc.perform(get("/api/diet/log/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void addMeal_ShouldReturnUpdatedLog() throws Exception {
        MealEntryCreateDTO mealDto = new MealEntryCreateDTO(UUID.randomUUID(), 500.0);
        CalorieLogDTO updatedLog = new CalorieLogDTO();

        when(calorieLogService.addMealToLog(any(UUID.class), any(LocalDate.class), any(MealEntryCreateDTO.class)))
                .thenReturn(updatedLog);

        mockMvc.perform(post("/api/diet/log/{date}/meal", testDate)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mealDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void removeMeal_ShouldReturnUpdatedLog() throws Exception {
        UUID mealId = UUID.randomUUID();

        when(calorieLogService.removeMealFromLog(any(UUID.class), eq(mealId), any(LocalDate.class)))
                .thenReturn(new CalorieLogDTO());

        mockMvc.perform(delete("/api/diet/log/{date}/meal/{mealEntryId}", testDate, mealId))
                .andExpect(status().isOk());
    }
}