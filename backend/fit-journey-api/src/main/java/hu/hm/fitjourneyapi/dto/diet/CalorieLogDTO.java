package hu.hm.fitjourneyapi.dto.diet;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CalorieLogDTO {
    private UUID id;
    private LocalDate date;
    private List<MealEntryDTO> entries;
    private int totalCalories;
    private int totalProtein;
    private int totalCarbs;
    private int totalFats;
}
