package hu.hm.fitjourneyapi.dto.social.post;

import hu.hm.fitjourneyapi.model.enums.PostVisibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO {
    private String title;
    private String content;
    private UUID userId;
    @Builder.Default
    private PostVisibility visibility = PostVisibility.GLOBAL;

    public PostCreateDTO(String title, String content, UUID userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.visibility = PostVisibility.GLOBAL;
    }
}
