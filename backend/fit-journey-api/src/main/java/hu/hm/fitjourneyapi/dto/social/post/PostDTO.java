package hu.hm.fitjourneyapi.dto.social.post;

import hu.hm.fitjourneyapi.dto.social.comment.CommentDTO;
import hu.hm.fitjourneyapi.model.enums.PostVisibility;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private UUID id;
    private String title;
    private String content;
    private UUID userId;
    private String userName;
    private String userProfilePictureUrl;
    private String imageUrl;
    private PostVisibility visibility;
    private LocalDateTime sentTime;

    private long likeCount;
    private long commentCount;
    private boolean likedByCurrentUser;

    private List<CommentDTO> comments;
}
