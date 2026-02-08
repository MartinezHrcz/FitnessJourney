package hu.hm.fitjourneyapi.model.diet;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
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
