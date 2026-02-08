package hu.hm.fitjourneyapi.model.diet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CALORIE_LOGS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalorieLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MealEntry> entries;

    public int getTotalCalories() {
        return entries.stream().mapToInt(MealEntry::getCalculatedCalories).sum();
    }
}
