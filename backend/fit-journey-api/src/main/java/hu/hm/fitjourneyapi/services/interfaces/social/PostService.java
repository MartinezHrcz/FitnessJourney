package hu.hm.fitjourneyapi.services.interfaces.social;

import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import jakarta.persistence.PostUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostDTO getPostById(UUID id, UUID currentUserId);

    List<PostDTO> getPosts(UUID currentUserId);

    List<PostDTO> getPostsByUserId(UUID id);

    PostDTO updatePost(UUID id,PostUpdateDTO postUpdateDTO, UUID currentUserId);

    PostDTO createPost(PostCreateDTO postCreateDTO);

    PostDTO createPostWithImage(PostCreateDTO postCreateDTO, MultipartFile image);

    void likePost(UUID id, UUID userId);

    void deletePostById(UUID id);
}
