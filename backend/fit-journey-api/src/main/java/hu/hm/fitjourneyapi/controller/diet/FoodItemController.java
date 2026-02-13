package hu.hm.fitjourneyapi.controller.diet;

import hu.hm.fitjourneyapi.dto.diet.FoodItemCreateDTO;
import hu.hm.fitjourneyapi.dto.diet.FoodItemDTO;
import hu.hm.fitjourneyapi.services.interfaces.diet.FoodItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<List<FoodItemDTO>> searchFoods(@RequestParam String name, @RequestParam(required = false) Boolean defaults, Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        if (defaults != null) {
            return ResponseEntity.ok(foodItemService.searchFoods(name, defaults, currentUserId));
        }
        return ResponseEntity.ok(foodItemService.searchFoods(name, currentUserId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItemDTO> getFoodById(@PathVariable UUID id) {
        return ResponseEntity.ok(foodItemService.getFoodById(id));
    }

    @PostMapping
    public ResponseEntity<FoodItemDTO> createFoodItem(@Valid @RequestBody FoodItemCreateDTO dto, Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        return new ResponseEntity<>(foodItemService.createFoodItem(dto, currentUserId), HttpStatus.CREATED);
    }

    @GetMapping("/default")
    public ResponseEntity<List<FoodItemDTO>> getDefaultFoodItems() {
        return ResponseEntity.ok(foodItemService.getDefaultFoodItems());
    }

    @GetMapping("/user/me")
    public ResponseEntity<List<FoodItemDTO>> getUserCreatedFoodItems(Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(foodItemService.getUserCreatedFoodItems(currentUserId));
    }

    @DeleteMapping("/id")
    public ResponseEntity deleteFoodItem(@RequestParam UUID id, Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        try {
            foodItemService.DeleteFoodItem(id, currentUserId);
        }
        catch(Exception ex) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
