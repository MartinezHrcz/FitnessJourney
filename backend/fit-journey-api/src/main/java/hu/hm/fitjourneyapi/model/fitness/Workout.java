package hu.hm.fitjourneyapi.model.fitness;

import hu.hm.fitjourneyapi.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "WORKOUTS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Workout {
    @Id
    private UUID id;
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = true, length = 200)
    private String description;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate startDate;
    private int lengthInMins;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(nullable = false, name="user_id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();

    public void AddExercise(Exercise exercise) {
        this.exercises.add(exercise);
        exercise.setWorkout(this);
    }

    public void RemoveExercise(Exercise exercise) {
        this.exercises.remove(exercise);
        exercise.setWorkout(null);
    }

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID();
    }

}
