package hu.hm.fitjourneyapi.dto.social.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateMessageDTO {
    private String content;
}
