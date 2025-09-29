package hu.hm.fitjourneyapi.dto.social.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private long id;
    private String title;
    private String content;
    private long userId;
    private LocalDateTime sentTime;
}
