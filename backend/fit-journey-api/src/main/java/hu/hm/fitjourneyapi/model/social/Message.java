package hu.hm.fitjourneyapi.model.social;

import hu.hm.fitjourneyapi.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "MESSAGES")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private UUID id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="sender_id", nullable = false)
    private User sender;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name="recipient_id", nullable = false)
    private User recipient;

    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime sentTime;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID();
    }
}
