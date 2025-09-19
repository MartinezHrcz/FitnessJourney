package hu.hm.fitjourneyapi.model;

import hu.hm.fitjourneyapi.model.enums.Roles;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.model.social.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 100)
    private String email;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Column(nullable = false, length = 50)
    private String password;
    private String weight;
    private String height;
    private Roles role;

    private LocalDateTime creation_date = LocalDateTime.now();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workout> workouts = new ArrayList<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
}
