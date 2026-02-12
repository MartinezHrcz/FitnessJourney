package hu.hm.fitjourneyapi.dto.diet;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FoodItemDTO {
    private UUID id;
    private String name;
    private double servingSize;
    private String servingUnit;
    private int calories;
    private int protein;
    private int carbs;
    private int fats;

    private boolean isDefault;
    private String nutritionSummary;
}
