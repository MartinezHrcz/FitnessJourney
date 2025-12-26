package hu.hm.fitjourneyapi.dto.social.friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FriendCreateDTO {
    private UUID userId;
    private UUID friendId;
}
