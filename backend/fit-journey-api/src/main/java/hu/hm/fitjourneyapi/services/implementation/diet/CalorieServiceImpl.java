package hu.hm.fitjourneyapi.services.implementation.diet;

import hu.hm.fitjourneyapi.dto.diet.CalorieLogDTO;
import hu.hm.fitjourneyapi.dto.diet.MealEntryCreateDTO;
import hu.hm.fitjourneyapi.mapper.diet.CalorieLogMapper;
import hu.hm.fitjourneyapi.model.diet.CalorieLog;
import hu.hm.fitjourneyapi.model.diet.FoodItem;
import hu.hm.fitjourneyapi.model.diet.MealEntry;
import hu.hm.fitjourneyapi.repository.diet.CalorieLogRepository;
import hu.hm.fitjourneyapi.repository.diet.FoodItemRepository;
import hu.hm.fitjourneyapi.services.interfaces.diet.CalorieLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class CalorieServiceImpl implements CalorieLogService {
    private final CalorieLogRepository calorieLogRepository;
    private final FoodItemRepository foodItemRepository;
    private final CalorieLogMapper calorieLogMapper;

    public CalorieServiceImpl(CalorieLogRepository calorieLogRepository, FoodItemRepository foodItemRepository, CalorieLogMapper calorieLogMapper) {
        this.calorieLogRepository = calorieLogRepository;
        this.foodItemRepository = foodItemRepository;
        this.calorieLogMapper = calorieLogMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public CalorieLogDTO getDailyLog(UUID userId, LocalDate date) {
        CalorieLog log = calorieLogRepository.findByUserIdAndDate(userId, date)
                .orElseGet(() -> createEmptyLog(userId, date));
        return calorieLogMapper.toDto(log);
    }

    @Override
    @Transactional
    public CalorieLogDTO addMealToLog(UUID userId, LocalDate date, MealEntryCreateDTO mealDto) {
        CalorieLog log = calorieLogRepository.findByUserIdAndDate(userId, date)
                .orElseGet(() -> createEmptyLog(userId, date));

        FoodItem foodItem = foodItemRepository.findById(mealDto.getFoodItemId())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        MealEntry mealEntry = MealEntry.builder()
                .foodItem(foodItem)
                .calorieLog(log)
                .amount(mealDto.getQuantity())
                .build();

        if (log.getEntries() == null) {
            log.setEntries(new ArrayList<>());
        }

        log.getEntries().add(mealEntry);

        log = calorieLogRepository.save(log);

        return calorieLogMapper.toDto(log);
    }

    @Override
    @Transactional
    public CalorieLogDTO removeMealFromLog(UUID userId, UUID mealEntryId, LocalDate date) {
        CalorieLog log = calorieLogRepository.findByUserIdAndDate(userId, date)
                .orElseThrow(() -> new RuntimeException("Log not found for this date"));

        log.getEntries().removeIf(entry -> entry.getId().equals(mealEntryId));

        return calorieLogMapper.toDto(calorieLogRepository.save(log));
    }

    private CalorieLog createEmptyLog(UUID userId, LocalDate date) {
        return CalorieLog.builder()
                .userId(userId)
                .date(date)
                .entries(new ArrayList<>())
                .build();
    }
}
