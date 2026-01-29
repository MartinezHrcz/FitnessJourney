package hu.hm.fitjourneyapi.model.social;

import hu.hm.fitjourneyapi.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "POSTS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(nullable = false, name="user_id")
    private User user;
    private String title;
    private String content;
    private String imageUrl;

    @ElementCollection
    @CollectionTable(name = "post_likes", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "user_id")
    private Set<UUID> likedByUsers;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime sentTime;
}
