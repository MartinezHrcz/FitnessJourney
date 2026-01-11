package hu.hm.fitjourneyapi.model.social;

import hu.hm.fitjourneyapi.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
    private UUID id;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(nullable = false, name="user_id")
    private User user;
    private String title;
    private String content;
    private String imageUrl;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime sentTime;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
