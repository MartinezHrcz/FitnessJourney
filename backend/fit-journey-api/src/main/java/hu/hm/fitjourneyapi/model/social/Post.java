package hu.hm.fitjourneyapi.model.social;

import hu.hm.fitjourneyapi.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "POSTS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(nullable = false, name="user_id")
    private User user;
    private String title;
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime sentTime;
}
