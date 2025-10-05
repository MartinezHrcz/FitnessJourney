package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;

import java.time.LocalDateTime;

public class PostsTestFactory {
    public static Post getPost(User user){
        Post post = Post.builder()
                .id(1L)
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
                .id(1L)
                .userId(1L)
                .title("Test Post")
                .content("This is test content!")
                .sentTime(LocalDateTime.now())
                .build();
        return dto;
    }
}
