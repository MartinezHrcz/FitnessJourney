package hu.hm.fitjourneyapi.services.interfaces.social;

import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import jakarta.persistence.PostUpdate;

import java.util.List;

public interface PostService {
    PostDTO getPostById(long id);

    List<PostDTO> getPosts();

    List<PostDTO> getPostsByUserId(long id);

    PostDTO updatePost(long id,PostUpdateDTO postUpdateDTO);

    PostDTO createPost(PostCreateDTO postCreateDTO);

    void deletePostById(long id);
}
