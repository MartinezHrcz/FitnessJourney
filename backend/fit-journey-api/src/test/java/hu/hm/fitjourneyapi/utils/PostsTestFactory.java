package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostsTestFactory {
    public static Post getPost(User user){
        Post post = Post.builder()
                .id(UUID.randomUUID())
                .user(user)
                .title("Test Post")
                .content("This is test content!")
                .sentTime(LocalDateTime.now())
                .build();
        return post;
    }

    public static PostDTO getPostDTO(){
        PostDTO dto = PostDTO
                .builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .title("Test Post")
                .content("This is test content!")
                .sentTime(LocalDateTime.now())
                .build();
        return dto;
    }
}
