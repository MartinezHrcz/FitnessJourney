package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import hu.hm.fitjourneyapi.exception.social.post.PostNotFoundException;
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
        log.debug("Fetching post by id: {}", id);
        Post post = postRepository.findById(id).orElseThrow(()->
        {
            log.warn("Post not found with id: {}", id);
            return new PostNotFoundException("Post not found with id: " + id);
        }
        );
        log.info("Fetched post with id: {} ",id);
        return postMapper.toPostDTO(post);
    }

    @Override
    public List<PostDTO> getPosts() {
        log.debug("Fetching posts");
        List<Post> posts = postRepository.findAll();
        log.info("Fetched posts");
        return postMapper.toListPostDTO(posts);
    }

    @Override
    public List<PostDTO> getPostsByUserId(long id) {
        log.debug("Fetching posts by user id: {}", id);
        List<Post> posts = postRepository.findPostsByUserId(id);
        log.info("Fetched posts by user id: {} ",id);
        return postMapper.toListPostDTO(posts);
    }

    @Override
    public PostDTO updatePost(long id, PostUpdateDTO postUpdateDTO) {
        log.debug("Attempting to update post by id: {}", id);
        Post post = postRepository.findById(id).orElseThrow(
                ()->{
                    log.warn("Post not found by id: {}", id);
                    return new PostNotFoundException("Post not found by id: " + id);
                }
        );
        post.setTitle(postUpdateDTO.getTitle());
        post.setContent(postUpdateDTO.getContent());

        log.info("Updated post with id: {} ",id);
        postRepository.save(post);
        return postMapper.toPostDTO(post);

    }

    @Override
    public PostDTO createPost(PostCreateDTO postCreateDTO) {
        return null;
    }

    @Override
    public void deletePostById(long id) {

    }
}
