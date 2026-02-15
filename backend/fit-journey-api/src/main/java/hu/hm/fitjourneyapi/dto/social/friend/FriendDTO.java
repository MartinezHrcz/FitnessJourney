package hu.hm.fitjourneyapi.dto.social.friend;

import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@NoArgsConstructor
@Builder
@Setter
@AllArgsConstructor
public class FriendDTO {
    private UUID id;
    private UUID userId;
    private UUID friendId;
    private String friendName;
    private String friendEmail;
    private boolean isRequester;
    private FriendStatus status;
    private LocalDateTime requestedTime;
}
