package hu.hm.fitjourneyapi.service.diet;

import hu.hm.fitjourneyapi.dto.diet.FoodItemCreateDTO;
import hu.hm.fitjourneyapi.dto.diet.FoodItemDTO;
import hu.hm.fitjourneyapi.mapper.diet.FoodItemMapper;
import hu.hm.fitjourneyapi.model.diet.FoodItem;
import hu.hm.fitjourneyapi.repository.diet.FoodItemRepository;
import hu.hm.fitjourneyapi.services.implementation.diet.FoodItemServiceImpl;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FoodItemServiceTests {

    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private FoodItemMapper foodItemMapper;

    @InjectMocks
    private FoodItemServiceImpl foodItemService;

    private FoodItem foodItem;
    private FoodItemDTO foodItemDTO;
    private UUID userId;
    private UUID foodId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        foodId = UUID.randomUUID();

        foodItem = new FoodItem();
        foodItem.setId(foodId);
        foodItem.setName("Chicken Breast");
        foodItem.setUserId(userId);

        foodItemDTO = new FoodItemDTO();
        foodItemDTO.setId(foodId);
        foodItemDTO.setName("Chicken Breast");
    }

    @Test
    void createFoodItem_WithUser_Success() {
        FoodItemCreateDTO createDTO = FoodItemCreateDTO.builder().build();
        when(foodItemMapper.toEntity(createDTO, userId)).thenReturn(foodItem);
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(foodItem);
        when(foodItemMapper.toDto(foodItem)).thenReturn(foodItemDTO);

        FoodItemDTO result = foodItemService.createFoodItem(createDTO, userId);

        assertNotNull(result);
        verify(foodItemRepository).save(foodItem);
    }

    @Test
    void searchFoods_FiltersCorrectly() {
        String query = "Chicken";
        FoodItem defaultFood = new FoodItem();
        defaultFood.setName("Chicken (Default)");
        defaultFood.setUserId(null);

        FoodItem otherUserFood = new FoodItem();
        otherUserFood.setName("Chicken (Other)");
        otherUserFood.setUserId(UUID.randomUUID());

        when(foodItemRepository.findByNameContainingIgnoreCase(query))
                .thenReturn(List.of(foodItem, defaultFood, otherUserFood));
        when(foodItemMapper.toDto(any())).thenReturn(foodItemDTO);

        List<FoodItemDTO> result = foodItemService.searchFoods(query, userId);

        assertEquals(2, result.size());
    }

    @Test
    void getFoodById_NotFound_ThrowsException() {
        when(foodItemRepository.findById(foodId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> foodItemService.getFoodById(foodId));
    }

    @Test
    void deleteFoodItem_Success() throws BadRequestException {
        when(foodItemRepository.findById(foodId)).thenReturn(Optional.of(foodItem));

        foodItemService.DeleteFoodItem(foodId, userId);

        verify(foodItemRepository).delete(foodItem);
    }

    @Test
    void deleteFoodItem_WrongUser_ThrowsBadRequest() {
        UUID differentUserId = UUID.randomUUID();
        when(foodItemRepository.findById(foodId)).thenReturn(Optional.of(foodItem));

        assertThrows(BadRequestException.class, () -> foodItemService.DeleteFoodItem(foodId, differentUserId));
    }

    @Test
    void getDefaultFoodItems_FiltersOnlyDefaults() {
        FoodItem defaultItem = new FoodItem();
        defaultItem.setUserId(null);
        FoodItem nonDefaultItem = new FoodItem();
        nonDefaultItem.setUserId(UUID.randomUUID());

        when(foodItemRepository.findAll()).thenReturn(List.of(defaultItem, nonDefaultItem));
        when(foodItemMapper.toDto(any())).thenReturn(FoodItemDTO.builder().build());

        List<FoodItemDTO> result = foodItemService.getDefaultFoodItems();

        assertEquals(1, result.size());
    }
}