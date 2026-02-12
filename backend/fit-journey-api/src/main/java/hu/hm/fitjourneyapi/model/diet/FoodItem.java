package hu.hm.fitjourneyapi.model.diet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "FOOD_ITEMS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private double servingSize;
    private String servingUnit;

    private int calories;
    private int protein;
    private int carbs;
    private int fats;

    @Column(nullable = true)
    private UUID userId;

    public boolean isDefault() {
        return userId == null;
    }
}
