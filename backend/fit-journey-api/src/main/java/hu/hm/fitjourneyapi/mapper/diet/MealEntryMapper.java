package hu.hm.fitjourneyapi.mapper.diet;

import hu.hm.fitjourneyapi.dto.diet.MealEntryDTO;
import hu.hm.fitjourneyapi.model.diet.MealEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MealEntryMapper {

    @Mapping(target = "foodName", source = "foodItem.name")
    @Mapping(target = "unit", source = "foodItem.servingUnit")
    @Mapping(target = "totalCalories", expression = "java((int)(entity.getFoodItem().getCalories() * entity.getAmount()))")
    @Mapping(target = "totalProtein", expression = "java((int)(entity.getFoodItem().getProtein() * entity.getAmount()))")
    @Mapping(target = "totalCarbs", expression = "java((int)(entity.getFoodItem().getCarbs() * entity.getAmount()))")
    @Mapping(target = "totalFats", expression = "java((int)(entity.getFoodItem().getFats() * entity.getAmount()))")
    MealEntryDTO toResponseDto(MealEntry entity);
}