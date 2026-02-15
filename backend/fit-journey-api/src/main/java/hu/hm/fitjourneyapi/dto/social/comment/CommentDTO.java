package hu.hm.fitjourneyapi.dto.social.comment;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private UUID id;
    private UUID postId;
    private UUID userId;
    private String userName;
    private String userProfilePicture;
    private String content;
    private LocalDateTime sentTime;
}