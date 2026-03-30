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
public class PostUpdateDTO {
    private String title;
    private String content;
    private UUID userId;
    private PostVisibility visibility;
}
