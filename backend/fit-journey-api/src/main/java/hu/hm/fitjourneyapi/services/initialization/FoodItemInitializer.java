package hu.hm.fitjourneyapi.services.initialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.model.diet.FoodItem;
import hu.hm.fitjourneyapi.repository.diet.FoodItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class FoodItemInitializer {

    private final FoodItemRepository foodItemRepository;
    private final ObjectMapper objectMapper;

    public FoodItemInitializer(FoodItemRepository foodItemRepository, ObjectMapper objectMapper) {
        this.foodItemRepository = foodItemRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        log.info("Fooditem initialization started");
        try (InputStream is = FoodItemInitializer.class.getClassLoader().getResourceAsStream("defaultFoodItems.json"))
        {
            JsonNode defaultFoodItems = objectMapper.readTree(is).get("foodItems");

            for (JsonNode foodItem : defaultFoodItems) {

                String name = foodItem.get("name").asText();

                if (foodItemRepository.existsByNameIgnoreCase(name)) continue;

                FoodItem initializedFoodItem =
                        FoodItem.builder()
                            .name(name)
                            .calories(foodItem.get("calories").asInt())
                            .protein(foodItem.get("protein").asInt())
                            .carbs(foodItem.get("carbs").asInt())
                            .fats(foodItem.get("fats").asInt())
                            .servingSize(foodItem.get("servingSize").asInt())
                            .servingUnit(foodItem.get("servingUnit").asText())
                            .userId(null)
                            .build();

                foodItemRepository.save(initializedFoodItem);
            }
        }
        catch (IOException e)
        {
            log.error("Food items initialization failed", e);
        }

    }
}
