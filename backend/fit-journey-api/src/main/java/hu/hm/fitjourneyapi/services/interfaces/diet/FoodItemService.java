package hu.hm.fitjourneyapi.services.interfaces.diet;

import hu.hm.fitjourneyapi.dto.diet.FoodItemCreateDTO;
import hu.hm.fitjourneyapi.dto.diet.FoodItemDTO;

import java.util.List;
import java.util.UUID;

public interface FoodItemService {
    FoodItemDTO createFoodItem(FoodItemCreateDTO dto);
    List<FoodItemDTO> searchFoods(String name);
    FoodItemDTO getFoodById(UUID id);
}
