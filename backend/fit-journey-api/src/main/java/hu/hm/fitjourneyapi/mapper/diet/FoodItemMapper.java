package hu.hm.fitjourneyapi.mapper.diet;

import hu.hm.fitjourneyapi.dto.diet.FoodItemCreateDTO;
import hu.hm.fitjourneyapi.dto.diet.FoodItemDTO;
import hu.hm.fitjourneyapi.model.diet.FoodItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FoodItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    FoodItem toEntity(FoodItemCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", source = "userId")
    FoodItem toEntity(FoodItemCreateDTO dto, UUID userId);

    @Mapping(target = "nutritionSummary", expression = "java(formatSummary(foodItem))")
    FoodItemDTO toDto(FoodItem foodItem);

    default String formatSummary(FoodItem foodItem) {
        return String.format("%d kcal per %.1f %s",
                foodItem.getCalories(),
                foodItem.getServingSize(),
                foodItem.getServingUnit());
    }
}
