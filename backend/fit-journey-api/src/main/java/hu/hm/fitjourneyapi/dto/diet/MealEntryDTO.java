package hu.hm.fitjourneyapi.dto.diet;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MealEntryDTO {
    private UUID id;
    private String foodName;
    private double quantity;
    private String unit;
    private int totalCalories;
    private int totalProtein;
    private int totalCarbs;
    private int totalFats;
}
