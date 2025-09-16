package hu.hm.fitjourneyapi.model.social;


import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Friend {
    private int id;
    private User sender;
    private User recipient;
    private FriendStatus status;
    private LocalDateTime requestedTime = LocalDateTime.now();
}
