package hu.hm.fitjourneyapi.services.implementation.diet;

import hu.hm.fitjourneyapi.dto.diet.FoodItemCreateDTO;
import hu.hm.fitjourneyapi.dto.diet.FoodItemDTO;
import hu.hm.fitjourneyapi.mapper.diet.FoodItemMapper;
import hu.hm.fitjourneyapi.model.diet.FoodItem;
import hu.hm.fitjourneyapi.repository.diet.FoodItemRepository;
import hu.hm.fitjourneyapi.services.interfaces.diet.FoodItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public FoodItemDTO createFoodItem(FoodItemCreateDTO dto, UUID userId) {
        FoodItem foodItem = foodItemMapper.toEntity(dto, userId);

        FoodItem savedFood = foodItemRepository.save(foodItem);
        return foodItemMapper.toDto(savedFood);
    }

    @Override
    public List<FoodItemDTO> searchFoods(String name, UUID userId) {
        List<FoodItemDTO> foodItemDTOS = foodItemRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .filter(foodItem ->  foodItem.getUserId() == null || foodItem.getUserId().equals(userId))
                .map(foodItemMapper::toDto)
                .toList();

        log.info("Returned {} number of food items", (long) foodItemDTOS.size());
        return foodItemDTOS;
    }

    @Override
    public List<FoodItemDTO> searchFoods(String name, boolean defaults, UUID userId) {
        return foodItemRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .filter(x -> defaults == x.isDefault())
                .map(foodItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FoodItemDTO getFoodById(UUID id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Food item not found with id: " + id));
        return foodItemMapper.toDto(foodItem);
    }

    @Override
    public List<FoodItemDTO> getDefaultFoodItems() {
        return foodItemRepository.findAll()
                .stream()
                .filter(FoodItem::isDefault)
                .map(foodItemMapper::toDto)
                .collect(Collectors.toList());    }

    @Override
    public List<FoodItemDTO> getUserCreatedFoodItems(UUID userId) {
        return foodItemRepository.findAll()
                .stream()
                .filter(x -> x.getUserId().equals(userId))
                .map(foodItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void DeleteFoodItem(UUID id, UUID userId) throws BadRequestException {
        FoodItem item = foodItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Food item not found with id: " + id));
        if (!item.getUserId().equals(userId)) {
            throw new BadRequestException("Food item was created by other user");
        }
        foodItemRepository.delete(item);
    }
}
