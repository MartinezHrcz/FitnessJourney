package hu.hm.fitjourneyapi.services.interfaces.social;

import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import jakarta.persistence.PostUpdate;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostDTO getPostById(UUID id);

    List<PostDTO> getPosts();

    List<PostDTO> getPostsByUserId(UUID id);

    PostDTO updatePost(UUID id,PostUpdateDTO postUpdateDTO);

    PostDTO createPost(PostCreateDTO postCreateDTO);

    void deletePostById(UUID id);
}
