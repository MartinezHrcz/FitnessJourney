package hu.hm.fitjourneyapi.dto.diet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class MealEntryCreateDTO {
    @NotNull(message = "Food item must be selected")
    private UUID foodItemId;

    @Positive(message = "Quantity must be greater than zero")
    private double quantity;
}
