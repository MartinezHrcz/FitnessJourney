package hu.hm.fitjourneyapi.services.interfaces.diet;

import hu.hm.fitjourneyapi.dto.diet.CalorieLogDTO;
import hu.hm.fitjourneyapi.dto.diet.MealEntryCreateDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CalorieLogService {
    CalorieLogDTO getDailyLog(UUID userId, LocalDate date);

    List<CalorieLogDTO> getHistoryLogs(UUID userId);

    CalorieLogDTO addMealToLog(UUID userId, LocalDate date, MealEntryCreateDTO mealDto);

    CalorieLogDTO removeMealFromLog(UUID userId, UUID mealEntryId, LocalDate date);
}
