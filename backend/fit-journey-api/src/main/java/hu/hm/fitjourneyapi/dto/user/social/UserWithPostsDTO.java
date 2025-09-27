package hu.hm.fitjourneyapi.dto.user.social;

import hu.hm.fitjourneyapi.dto.user.AbstractUserDTO;
import hu.hm.fitjourneyapi.model.social.Post;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWithPostsDTO extends AbstractUserDTO {
    private long id;
    private List<Post> posts;
}
