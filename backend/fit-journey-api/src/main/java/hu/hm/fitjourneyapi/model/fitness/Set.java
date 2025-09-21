package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    @ManyToOne
    @JoinColumn(name = "excercise_id")
    private Excercise excercise;
}
