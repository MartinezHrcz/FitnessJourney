package hu.hm.fitjourneyapi.dto.social.post;

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
}
