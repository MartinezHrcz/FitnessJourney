package hu.hm.fitjourneyapi.mapper.diet;

import hu.hm.fitjourneyapi.dto.diet.MealEntryDTO;
import hu.hm.fitjourneyapi.model.diet.MealEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MealEntryMapper {

    @Mapping(target = "foodName", source = "foodItem.name")
    @Mapping(target = "unit", source = "foodItem.servingUnit")
    @Mapping(target = "totalCalories", expression = "java((int)(entity.getFoodItem().getCalories() * entity.getQuantity()))")
    @Mapping(target = "totalProtein", expression = "java((int)(entity.getFoodItem().getProtein() * entity.getQuantity()))")
    @Mapping(target = "totalCarbs", expression = "java((int)(entity.getFoodItem().getCarbs() * entity.getQuantity()))")
    @Mapping(target = "totalFats", expression = "java((int)(entity.getFoodItem().getFats() * entity.getQuantity()))")
    MealEntryDTO toResponseDto(MealEntry entity);
}