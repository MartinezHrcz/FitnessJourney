package hu.hm.fitjourneyapi.services.implementation.diet;

import hu.hm.fitjourneyapi.dto.diet.FoodItemCreateDTO;
import hu.hm.fitjourneyapi.dto.diet.FoodItemDTO;
import hu.hm.fitjourneyapi.mapper.diet.FoodItemMapper;
import hu.hm.fitjourneyapi.model.diet.FoodItem;
import hu.hm.fitjourneyapi.repository.diet.FoodItemRepository;
import hu.hm.fitjourneyapi.services.interfaces.diet.FoodItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final FoodItemMapper foodItemMapper;

    public FoodItemServiceImpl(FoodItemRepository foodItemRepository, FoodItemMapper foodItemMapper) {
        this.foodItemRepository = foodItemRepository;
        this.foodItemMapper = foodItemMapper;
    }

    @Override
    public FoodItemDTO createFoodItem(FoodItemCreateDTO dto) {
        FoodItem foodItem = foodItemMapper.toEntity(dto);

        FoodItem savedFood = foodItemRepository.save(foodItem);
        return foodItemMapper.toDto(savedFood);
    }

    @Override
    public List<FoodItemDTO> searchFoods(String name) {
        return foodItemRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(foodItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FoodItemDTO getFoodById(UUID id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Food item not found with id: " + id));
        return foodItemMapper.toDto(foodItem);
    }
}
