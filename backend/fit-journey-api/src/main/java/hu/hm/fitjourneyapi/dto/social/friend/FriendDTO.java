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
    private UUID id;
    private UUID userId;
    private UUID friendId;
    private String friendName;
    private String friendEmail;
    private boolean isRequester;
    private FriendStatus status;
    private LocalDateTime requestedTime;

}
