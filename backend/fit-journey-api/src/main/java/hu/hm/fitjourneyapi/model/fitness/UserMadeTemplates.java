package hu.hm.fitjourneyapi.model.fitness;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name= "USER_EXERCISE_TEMPLATES")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMadeTemplates {
    @Id
    private UUID id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    @Column(length = 400)
    private String description;

    @Enumerated(EnumType.STRING)
    private WeightType weightType;

    @Enumerated(EnumType.STRING)
    private ExerciseTypes type;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
