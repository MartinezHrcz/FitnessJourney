package hu.hm.fitjourneyapi.model.social;

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
    private UUID userId;
    private String text;
    private LocalDateTime sentTime;

    @PrePersist
    protected void onCreate() {
        this.id = UUID.randomUUID();
        this.sentTime = LocalDateTime.now();
    }
}
