package hu.hm.fitjourneyapi.controller.diet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.dto.diet.FoodItemDTO;
import hu.hm.fitjourneyapi.services.interfaces.diet.FoodItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoodItemController.class)
public class FoodItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FoodItemService foodItemService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String MOCK_USER_ID = "99371d57-e1f7-4f17-8d30-e6406daad176";

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void searchFoods_WithDefaults_ShouldReturnFilteredList() throws Exception {
        FoodItemDTO apple = new FoodItemDTO();
        apple.setName("Apple");

        when(foodItemService.searchFoods(eq("apple"), eq(true), eq(UUID.fromString(MOCK_USER_ID))))
                .thenReturn(List.of(apple));

        mockMvc.perform(get("/api/food-items/search")
                        .param("name", "apple")
                        .param("defaults", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Apple"));
    }

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void deleteFoodItem_Success_Returns200() throws Exception {
        UUID foodId = UUID.randomUUID();

        mockMvc.perform(delete("/api/food-items/id")
                        .param("id", foodId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = MOCK_USER_ID)
    void deleteFoodItem_ServiceThrowsException_ReturnsBadRequest() throws Exception {
        UUID foodId = UUID.randomUUID();

        doThrow(new RuntimeException("Not authorized")).when(foodItemService)
                .DeleteFoodItem(eq(foodId), eq(UUID.fromString(MOCK_USER_ID)));

        mockMvc.perform(delete("/api/food-items/id")
                        .param("id", foodId.toString()))
                .andExpect(status().isBadRequest());
    }
}