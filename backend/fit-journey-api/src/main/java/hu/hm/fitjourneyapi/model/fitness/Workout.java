package hu.hm.fitjourneyapi.model.fitness;

import hu.hm.fitjourneyapi.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "WORKOUTS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 50)
    private String name;
    private LocalDate startDate = LocalDate.now();
    private int lengthInMins;
    @ManyToOne
    @JoinColumn(nullable = false, name="user_id")
    private User user;
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Excercise> excercises = new ArrayList<>();
}
