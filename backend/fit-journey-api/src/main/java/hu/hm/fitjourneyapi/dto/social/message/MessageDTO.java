package hu.hm.fitjourneyapi.dto.social.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class MessageDTO {
    private long senderId;
    private long recipientId;
    private String content;
    private long sentTime;
}
