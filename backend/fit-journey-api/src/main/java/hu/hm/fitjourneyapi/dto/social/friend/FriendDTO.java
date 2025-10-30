package hu.hm.fitjourneyapi.dto.social.friend;

import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FriendDTO {
    private long id;
    private UUID userId;
    private UUID friendId;
    private FriendStatus status;
    private LocalDateTime requestedTime;

}
