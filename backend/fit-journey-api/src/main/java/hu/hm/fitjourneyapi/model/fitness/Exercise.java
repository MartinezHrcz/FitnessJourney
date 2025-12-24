package hu.hm.fitjourneyapi.model.fitness;

import hu.hm.fitjourneyapi.exception.fitness.setExceptions.InvalidSetType;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "EXERCISES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    @Id
    private UUID id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private WeightType weightType = WeightType.NOT_GIVEN;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(length = 400)
    private String description;
    @Builder.Default
    private ExerciseTypes type = ExerciseTypes.NOT_GIVEN;

    @ToString.Exclude
    @ManyToOne
    @Nullable
    @JoinColumn(name="workout_id")
    private Workout workout;

    @Builder.Default
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Set> sets = new ArrayList<>();

    private static final Map<ExerciseTypes, Class<? extends Set>> VALID_SET_TYPES =
            Map.of(
                    ExerciseTypes.RESISTANCE, StrengthSet.class,
                    ExerciseTypes.BODY_WEIGHT, StrengthSet.class,
                    ExerciseTypes.NOT_GIVEN, StrengthSet.class,
                    ExerciseTypes.CARDIO, CardioSet.class,
                    ExerciseTypes.FLEXIBILITY, FlexibilitySet.class
            );

    public void addSet(Set set) {

        Class<? extends Set> expectedType = VALID_SET_TYPES.get(this.type);

        if (expectedType == null) {
            throw new InvalidSetType("Unsupported exercise type: " + this.type);
        }

        if (!expectedType.isAssignableFrom(set.getClass())) {
            throw new InvalidSetType("Invalid exercise type: " + this.type);
        }

        switch (this.type)
        {
            case RESISTANCE:
                this.weightType = WeightType.FREE_WEIGHT;
                break;
            case BODY_WEIGHT:
                this.weightType = WeightType.BODYWEIGHT;
                break;
        }

        sets.add(set);
        set.setExercise(this);
    }

    public void removeSet(Set set) {
        sets.remove(set);
        set.setExercise(null);
    }

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID();
    }
}