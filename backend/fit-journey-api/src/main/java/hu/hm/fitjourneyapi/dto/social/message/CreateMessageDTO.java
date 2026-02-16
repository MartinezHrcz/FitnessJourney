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
public class CreateMessageDTO {
    private UUID senderId;
    private UUID recipientId;
    private String content;
}
