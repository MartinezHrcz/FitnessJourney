package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

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
    @ManyToOne
    @JoinColumn(name="workout_id")
    private Workout workout;
    @OneToMany(mappedBy = "excercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Set> sets = new ArrayList<>();
}
