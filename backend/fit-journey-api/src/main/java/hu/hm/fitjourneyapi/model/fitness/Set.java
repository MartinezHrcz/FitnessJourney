package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "SETS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int reps;
    private double weight;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "excercise_id")
    private Excercise excercise;
}
