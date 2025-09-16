package hu.hm.fitjourneyapi.model.social;

import hu.hm.fitjourneyapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int id;
    private User sender;
    private String title;
    private String content;
    private LocalDateTime sentTime = LocalDateTime.now();
}
