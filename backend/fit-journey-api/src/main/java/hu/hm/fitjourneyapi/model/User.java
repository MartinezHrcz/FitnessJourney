package hu.hm.fitjourneyapi.model;

import hu.hm.fitjourneyapi.model.enums.Roles;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.model.social.Friend;
import hu.hm.fitjourneyapi.model.social.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, length = 100, unique = true)
    private String name;
    @Column(nullable = false, length = 100)
    private String email;
    private LocalDate birthday;
    @Column(nullable = false, length = 100)
    private String password;
    private float weightInKg;
    private float heightInCm;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creation_date;

    @LastModifiedDate
    private LocalDateTime last_modified_date;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workout> workouts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friends = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friend> friendOf = new ArrayList<>();

    public void addFriend(Friend friend) {
        this.friends.add(friend);
        friend.setUser(this);
    }

    public void removeFriend(Friend friend) {
        this.friends.remove(friend);
        friend.setUser(null);
    }

    public void addPost(Post post) {
        this.posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
        post.setUser(null);
    }

    public void addWorkout(Workout workout) {
        this.workouts.add(workout);
        workout.setUser(this);
    }

    public void removeWorkout(Workout workout) {
        this.workouts.remove(workout);
        workout.setUser(null);
    }

}
