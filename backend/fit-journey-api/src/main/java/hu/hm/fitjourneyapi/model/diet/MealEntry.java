package hu.hm.fitjourneyapi.model.diet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calorie_log_id")
    private CalorieLog calorieLog;

    @ManyToOne
    @JoinColumn(name = "fooditem_id")
    private FoodItem foodItem;

    private double amount;

    public int getCalculatedCalories() {
        return (int) (foodItem.getCalories() * amount);
    }
}
