package hu.hm.fitjourneyapi.dto.social.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
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