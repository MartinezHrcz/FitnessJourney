package hu.hm.fitjourneyapi.model.fitness;

import hu.hm.fitjourneyapi.exception.fitness.setExceptions.InvalidSetType;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EXERCISES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private WeightType weightType = WeightType.NOT_GIVEN;

    @Column(unique = true, nullable = false, length = 50)
    private String name;
    @Column(length = 100)
    private String description;
    @Builder.Default
    private ExerciseTypes type = ExerciseTypes.NOT_GIVEN;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="workout_id")
    private Workout workout;

    @Builder.Default
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Set> sets = new ArrayList<>();

    public void addSet(Set set) {
        if (this.type == ExerciseTypes.RESISTANCE && !(set instanceof StrengthSet)) {
            throw new InvalidSetType("Resistance exercise can only be used with strength sets");
        }
        if (this.type == ExerciseTypes.CARDIO && !(set instanceof CardioSet)) {
            throw new InvalidSetType("Cardio exercise can only be used with cardio sets");
        }
        if (this.type == ExerciseTypes.FLEXIBILITY && !(set instanceof FlexibilitySet)) {
            throw new InvalidSetType("Flexibility exercise can only be used with flexibility sets");
        }
        switch (this.type)
        {
            case RESISTANCE:
                this.weightType = WeightType.FREE_WEIGHT;
                break;
            case BODYWEIGHT:
                this.weightType = WeightType.BODYWEIGHT;
                break;
        }
        sets.add(set);
        set.setExercise(this);
    }

    public void AddSet(Set set) {
        sets.add(set);
        set.setExercise(this);
    }

    public void removeSet(Set set) {
        sets.remove(set);
        set.setExercise(null);
    }
}
