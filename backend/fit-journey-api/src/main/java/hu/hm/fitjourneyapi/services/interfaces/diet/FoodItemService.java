package hu.hm.fitjourneyapi.services.interfaces.diet;

import hu.hm.fitjourneyapi.dto.diet.FoodItemCreateDTO;
import hu.hm.fitjourneyapi.dto.diet.FoodItemDTO;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.UUID;

public interface FoodItemService {
    FoodItemDTO createFoodItem(FoodItemCreateDTO dto);
    FoodItemDTO createFoodItem(FoodItemCreateDTO dto, UUID userId);
    List<FoodItemDTO> searchFoods(String name,  UUID userId);
    List<FoodItemDTO> searchFoods(String name, boolean defaults, UUID userId);
    FoodItemDTO getFoodById(UUID id);
    List<FoodItemDTO> getDefaultFoodItems();
    List<FoodItemDTO> getUserCreatedFoodItems(UUID userId);
    void DeleteFoodItem(UUID id, UUID userId) throws BadRequestException;
}
