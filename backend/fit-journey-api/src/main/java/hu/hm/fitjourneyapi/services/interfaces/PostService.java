package hu.hm.fitjourneyapi.services.interfaces;

import hu.hm.fitjourneyapi.dto.social.post.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO getPostById(long id);

    List<PostDTO> getPosts();

    List<PostDTO> getPostsByUserId(long id);

    PostDTO updatePost(PostDTO postDTO);

    PostDTO createPost(PostDTO postDTO);

    void deletePostById(long id);
}
