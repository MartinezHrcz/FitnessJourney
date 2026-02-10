package hu.hm.fitjourneyapi.dto.diet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@Builder
public class FoodItemCreateDTO {
    @NotBlank(message = "Food name is required")
    private String name;

    @Positive(message = "Serving size must be greater than zero")
    private double servingSize;

    @NotBlank(message = "Serving unit (g, ml, piece) is required")
    private String servingUnit;

    @PositiveOrZero(message = "Calories cannot be negative")
    private int calories;

    @PositiveOrZero
    private int protein;

    @PositiveOrZero
    private int carbs;

    @PositiveOrZero
    private int fats;
}
