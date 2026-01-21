package hu.hm.fitjourneyapi.model.social;

import hu.hm.fitjourneyapi.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "POSTS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String content;

    private LocalDateTime sentTime;

    @PrePersist
    protected void onCreate() {
        this.id = UUID.randomUUID();
        this.sentTime = LocalDateTime.now();
    }
}
