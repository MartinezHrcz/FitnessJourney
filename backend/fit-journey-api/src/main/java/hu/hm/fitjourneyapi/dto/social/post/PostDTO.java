package hu.hm.fitjourneyapi.dto.social.post;

import hu.hm.fitjourneyapi.dto.social.comment.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private UUID id;
    private String title;
    private String content;
    private UUID userId;
    private String userName;
    private String imageUrl;
    private LocalDateTime sentTime;

    private long likeCount;
    private long commentCount;
    private boolean likedByCurrentUser;

    private List<CommentDTO> comments;
}
