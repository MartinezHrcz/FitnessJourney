package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "SETS")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;


}
