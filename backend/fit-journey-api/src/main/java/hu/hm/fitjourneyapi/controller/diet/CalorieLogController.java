package hu.hm.fitjourneyapi.controller.diet;

import hu.hm.fitjourneyapi.dto.diet.CalorieLogDTO;
import hu.hm.fitjourneyapi.dto.diet.MealEntryCreateDTO;
import hu.hm.fitjourneyapi.services.interfaces.diet.CalorieLogService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/diet")
public class CalorieLogController {

    private final CalorieLogService calorieLogService;

    public CalorieLogController(CalorieLogService calorieLogService) {
        this.calorieLogService = calorieLogService;
    }

    @GetMapping("/log/{date}")
    public ResponseEntity<CalorieLogDTO> getDailyLog(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam UUID userId) {
        return ResponseEntity.ok(calorieLogService.getDailyLog(userId, date));
    }

    @PostMapping("/log/{date}/meal")
    public ResponseEntity<CalorieLogDTO> addMeal(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam UUID userId,
            @RequestBody MealEntryCreateDTO mealDto) {
        return ResponseEntity.ok(calorieLogService.addMealToLog(userId, date, mealDto));
    }

    @DeleteMapping("/log/{date}/meal/{mealEntryId}")
    public ResponseEntity<CalorieLogDTO> removeMeal(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PathVariable UUID mealEntryId,
            @RequestParam UUID userId) {
        return ResponseEntity.ok(calorieLogService.removeMealFromLog(userId, mealEntryId, date));
    }
}
