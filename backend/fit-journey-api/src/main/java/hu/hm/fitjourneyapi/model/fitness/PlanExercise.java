package hu.hm.fitjourneyapi.model.fitness;

import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "PLAN_EXERCISES")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID exerciseTemplateId;

    private String name;
    private ExerciseTypes type;

    private int targetSets;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    @ToString.Exclude
    private WorkoutPlan workoutPlan;
}
