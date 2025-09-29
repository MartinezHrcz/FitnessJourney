package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;

import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {
    public static PostDTO toPostDTO(Post post) {
        if (post == null) return null;
        return PostDTO
                .builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .sentTime(post.getSentTime())
                .build();
    }

    public static List<PostDTO> toListPostDTO(List<Post> posts) {
        if (posts == null) return null;
        return posts.stream().map(PostMapper::toPostDTO).collect(Collectors.toList());
    }

    public static Post toPost(PostDTO postDTO, User user) {
        if (postDTO == null) return null;
        return Post.builder()
                .user(user)
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .build();
    }
}
