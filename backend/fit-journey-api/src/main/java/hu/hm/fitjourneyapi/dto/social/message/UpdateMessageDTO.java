package hu.hm.fitjourneyapi.dto.social.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class UpdateMessageDTO {
    private String content;
}
