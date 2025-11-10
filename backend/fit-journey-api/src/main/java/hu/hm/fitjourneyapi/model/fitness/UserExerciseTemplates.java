package hu.hm.fitjourneyapi.model.fitness;

import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_EXERCISE_TEMPLATES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExerciseTemplates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(length = 400)
    private String description;

    @Enumerated(EnumType.STRING)
    private WeightType weightType;

    @Enumerated(EnumType.STRING)
    private ExerciseTypes type;
}
