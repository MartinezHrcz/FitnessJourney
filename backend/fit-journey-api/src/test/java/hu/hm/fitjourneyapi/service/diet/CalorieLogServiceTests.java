package hu.hm.fitjourneyapi.service.diet;

import hu.hm.fitjourneyapi.dto.diet.CalorieLogDTO;
import hu.hm.fitjourneyapi.dto.diet.MealEntryCreateDTO;
import hu.hm.fitjourneyapi.mapper.diet.CalorieLogMapper;
import hu.hm.fitjourneyapi.model.diet.CalorieLog;
import hu.hm.fitjourneyapi.model.diet.FoodItem;
import hu.hm.fitjourneyapi.model.diet.MealEntry;
import hu.hm.fitjourneyapi.repository.diet.CalorieLogRepository;
import hu.hm.fitjourneyapi.repository.diet.FoodItemRepository;
import hu.hm.fitjourneyapi.services.implementation.diet.CalorieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CalorieLogServiceTests {

    @Mock
    private CalorieLogRepository calorieLogRepository;
    @Mock
    private FoodItemRepository foodItemRepository;
    @Mock
    private CalorieLogMapper calorieLogMapper;

    @InjectMocks
    private CalorieServiceImpl calorieLogService;

    private UUID userId;
    private LocalDate today;
    private CalorieLog calorieLog;
    private CalorieLogDTO calorieLogDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        today = LocalDate.now();

        calorieLog = CalorieLog.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .date(today)
                .entries(new ArrayList<>())
                .build();

        calorieLogDTO = new CalorieLogDTO();
    }

    @Test
    void getDailyLog_ExistingLog_ReturnsLog() {
        when(calorieLogRepository.findByUserIdAndDate(userId, today)).thenReturn(Optional.of(calorieLog));
        when(calorieLogMapper.toDto(calorieLog)).thenReturn(calorieLogDTO);

        CalorieLogDTO result = calorieLogService.getDailyLog(userId, today);

        assertNotNull(result);
        verify(calorieLogRepository, never()).save(any());
    }

    @Test
    void addMealToLog_Success() {
        UUID foodId = UUID.randomUUID();
        MealEntryCreateDTO mealDto = new MealEntryCreateDTO(foodId, 250.0);
        FoodItem foodItem = new FoodItem();
        foodItem.setId(foodId);

        when(calorieLogRepository.findByUserIdAndDate(userId, today)).thenReturn(Optional.of(calorieLog));
        when(foodItemRepository.findById(foodId)).thenReturn(Optional.of(foodItem));
        when(calorieLogRepository.save(any(CalorieLog.class))).thenReturn(calorieLog);
        when(calorieLogMapper.toDto(calorieLog)).thenReturn(calorieLogDTO);

        CalorieLogDTO result = calorieLogService.addMealToLog(userId, today, mealDto);

        assertNotNull(result);
        assertEquals(1, calorieLog.getEntries().size());
        assertEquals(250.0, calorieLog.getEntries().getFirst().getAmount());
        verify(calorieLogRepository).save(calorieLog);
    }

    @Test
    void addMealToLog_FoodNotFound_ThrowsException() {
        MealEntryCreateDTO mealDto = new MealEntryCreateDTO(UUID.randomUUID(), 100.0);
        when(calorieLogRepository.findByUserIdAndDate(userId, today)).thenReturn(Optional.of(calorieLog));
        when(foodItemRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> calorieLogService.addMealToLog(userId, today, mealDto));
    }

    @Test
    void removeMealFromLog_Success() {
        UUID mealEntryId = UUID.randomUUID();
        MealEntry entry = new MealEntry();
        entry.setId(mealEntryId);
        calorieLog.getEntries().add(entry);

        when(calorieLogRepository.findByUserIdAndDate(userId, today)).thenReturn(Optional.of(calorieLog));
        when(calorieLogRepository.save(calorieLog)).thenReturn(calorieLog);
        when(calorieLogMapper.toDto(calorieLog)).thenReturn(calorieLogDTO);

        calorieLogService.removeMealFromLog(userId, mealEntryId, today);

        assertTrue(calorieLog.getEntries().isEmpty());
        verify(calorieLogRepository).save(calorieLog);
    }

    @Test
    void removeMealFromLog_LogNotFound_ThrowsException() {
        when(calorieLogRepository.findByUserIdAndDate(userId, today)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> calorieLogService.removeMealFromLog(userId, UUID.randomUUID(), today));
    }
}