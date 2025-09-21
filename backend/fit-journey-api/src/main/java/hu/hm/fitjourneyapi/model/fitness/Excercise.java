package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EXCERCISES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Excercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(length = 100)
    private String description;
    private String type;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="workout_id")
    private Workout workout;

    @Builder.Default
    @OneToMany(mappedBy = "excercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Set> sets = new ArrayList<>();
}
