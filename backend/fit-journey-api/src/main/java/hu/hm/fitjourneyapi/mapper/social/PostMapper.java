package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(source = "post.user.id", target = "userId")
    @Mapping(source = "post.user.name", target = "userName")
    @Mapping(target = "likeCount", expression = "java(post.getLikedByUsers() != null ? post.getLikedByUsers().size() : 0)")
    @Mapping(target = "commentCount", expression = "java(post.getComments() != null ? post.getComments().size() : 0)")
    @Mapping(target = "likedByCurrentUser", expression = "java(checkIfLiked(post, currentUserId))")
    PostDTO toPostDTO(Post post, @Context UUID currentUserId);

    default boolean checkIfLiked(Post post, @Context UUID currentUserId) {
        if (post.getLikedByUsers() == null || currentUserId == null) return false;
        return post.getLikedByUsers().contains(currentUserId);
    }

    List<PostDTO> toListPostDTO(List<Post> posts, @Context UUID currentUserId);

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
