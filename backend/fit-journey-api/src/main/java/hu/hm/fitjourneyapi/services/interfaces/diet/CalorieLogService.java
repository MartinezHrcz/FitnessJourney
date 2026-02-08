package hu.hm.fitjourneyapi.services.interfaces.diet;

import hu.hm.fitjourneyapi.dto.diet.CalorieLogDTO;
import hu.hm.fitjourneyapi.dto.diet.MealEntryCreateDTO;

import java.time.LocalDate;
import java.util.UUID;

public interface CalorieLogService {
    CalorieLogDTO getDailyLog(UUID userId, LocalDate date);

    CalorieLogDTO addMealToLog(UUID userId, LocalDate date, MealEntryCreateDTO mealDto);

    CalorieLogDTO removeMealFromLog(UUID userId, UUID mealEntryId, LocalDate date);
}
