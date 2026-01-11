package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(source = "user", target = "userId", qualifiedByName = "userToId")
    @Mapping(source = "user", target = "userName", qualifiedByName = "userToName")
    PostDTO toPostDTO(Post post);

    List<PostDTO> toListPostDTO(List<Post> posts);

    @Mapping(source = "dto.id", target = "id")
    @Mapping(target = "user", expression = "java(user)")
    Post toPost(PostDTO dto, User user);


    @Named("userToId")
    static UUID userToId(User user) {
        return user != null ? user.getId() : null;
    }

    @Named("userToName")
    static String userToName(User user) { return user != null ? user.getName() : null; }
}
