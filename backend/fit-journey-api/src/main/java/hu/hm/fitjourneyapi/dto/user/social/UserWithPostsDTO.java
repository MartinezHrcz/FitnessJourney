package hu.hm.fitjourneyapi.dto.user.social;

import hu.hm.fitjourneyapi.dto.user.AbstractUserDTO;
import hu.hm.fitjourneyapi.model.social.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWithPostsDTO extends AbstractUserDTO {
    private long id;
    private List<Post> posts;
}
