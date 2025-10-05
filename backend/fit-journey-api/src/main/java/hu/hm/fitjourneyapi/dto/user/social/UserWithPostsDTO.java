package hu.hm.fitjourneyapi.dto.user.social;

import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.user.AbstractUserDTO;
import hu.hm.fitjourneyapi.model.social.Post;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithPostsDTO extends AbstractUserDTO {
    private long id;
    private List<PostDTO> posts;
}
