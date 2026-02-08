package hu.hm.fitjourneyapi.mapper.diet;

import hu.hm.fitjourneyapi.dto.diet.CalorieLogDTO;
import hu.hm.fitjourneyapi.model.diet.CalorieLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MealEntryMapper.class})
public interface CalorieLogMapper {

    @Mapping(target = "totalCalories", expression = "java(calculateTotalCals(entity))")
    @Mapping(target = "totalProtein", expression = "java(calculateTotalProtein(entity))")
    @Mapping(target = "totalCarbs", expression = "java(calculateTotalCarbs(entity))")
    @Mapping(target = "totalFats", expression = "java(calculateTotalFats(entity))")
    CalorieLogDTO toDto(CalorieLog entity);

    default int calculateTotalCals(CalorieLog entity) {
        return entity.getEntries().stream()
                .mapToInt(e -> (int)(e.getFoodItem().getCalories() * e.getAmount()))
                .sum();
    }

    default int calculateTotalProtein(CalorieLog entity) {
        return entity.getEntries().stream()
                .mapToInt(e -> (int)(e.getFoodItem().getProtein() * e.getAmount()))
                .sum();
    }

    default int calculateTotalCarbs(CalorieLog entity) {
        return entity.getEntries().stream()
                .mapToInt(e -> (int)(e.getFoodItem().getCarbs() * e.getAmount()))
                .sum();
    }


    default int calculateTotalFats(CalorieLog entity) {
        return entity.getEntries().stream()
                .mapToInt(e -> (int)(e.getFoodItem().getFats() * e.getAmount()))
                .sum();
    }
}
