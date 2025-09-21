package hu.hm.fitjourneyapi.model.social;


import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name= "FRIENDS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FriendStatus status = FriendStatus.IN_PROGRESS;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime requestedTime = LocalDateTime.now();
}
