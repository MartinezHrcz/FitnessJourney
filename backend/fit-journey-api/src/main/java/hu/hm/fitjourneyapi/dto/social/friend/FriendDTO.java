package hu.hm.fitjourneyapi.dto.social.friend;

import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
public class FriendDTO {

    private long id;
    private long userId;
    private long friendId;
    private FriendStatus status;
    private LocalDateTime requestedTime;

}
