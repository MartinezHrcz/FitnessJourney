package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.mapper.social.PostMapper;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;


    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }


    @Override
    public PostDTO getPostById(long id) {
        return null;
    }

    @Override
    public List<PostDTO> getPosts() {
        return List.of();
    }

    @Override
    public List<PostDTO> getPostsByUserId(long id) {
        return List.of();
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO) {
        return null;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        return null;
    }

    @Override
    public void deletePostById(long id) {

    }
}
