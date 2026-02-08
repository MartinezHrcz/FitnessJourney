package hu.hm.fitjourneyapi.controller.diet;

import hu.hm.fitjourneyapi.dto.diet.FoodItemCreateDTO;
import hu.hm.fitjourneyapi.dto.diet.FoodItemDTO;
import hu.hm.fitjourneyapi.services.interfaces.diet.FoodItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/food-items")
public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodItemDTO>> searchFoods(@RequestParam String name) {
        return ResponseEntity.ok(foodItemService.searchFoods(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItemDTO> getFoodById(@PathVariable UUID id) {
        return ResponseEntity.ok(foodItemService.getFoodById(id));
    }

    @PostMapping
    public ResponseEntity<FoodItemDTO> createFoodItem(@Valid @RequestBody FoodItemCreateDTO dto) {
        return new ResponseEntity<>(foodItemService.createFoodItem(dto), HttpStatus.CREATED);
    }
}
