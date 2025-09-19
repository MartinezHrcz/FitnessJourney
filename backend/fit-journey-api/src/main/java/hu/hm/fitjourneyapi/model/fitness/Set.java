package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "SETS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int reps;
    @Column(nullable = false)
    private double weight;

    @ManyToOne
    @JoinColumn(name = "excercise_id")
    private Excercise excercise;
}
