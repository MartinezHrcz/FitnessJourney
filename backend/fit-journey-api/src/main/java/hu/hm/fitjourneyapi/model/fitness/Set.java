package hu.hm.fitjourneyapi.model.fitness;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "SETS")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public abstract class Set {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;


}
