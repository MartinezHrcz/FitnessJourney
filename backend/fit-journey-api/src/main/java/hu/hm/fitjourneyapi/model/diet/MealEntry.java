package hu.hm.fitjourneyapi.model.diet;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class MealEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private FoodItem foodItem;

    private double amount;

    public int getCalculatedCalories() {
        return (int) (foodItem.getCalories() * amount);
    }
}
