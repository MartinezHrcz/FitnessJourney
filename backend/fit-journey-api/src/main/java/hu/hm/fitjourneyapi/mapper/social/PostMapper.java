package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface PostMapper {

    @Mapping(source = "user", target = "userId", qualifiedByName = "userToId")
    PostDTO toPostDTO(Post post);

    List<PostDTO> toListPostDTO(List<Post> posts);

    @Mapping(target = "user", expression = "java(user)")
    Post toPost(PostDTO postDTO, User user);

    @Named("userToId")
    static Long userToId(User user) {
        return user != null ? user.getId() : null;
    }
}
