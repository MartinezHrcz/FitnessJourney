package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import hu.hm.fitjourneyapi.exception.social.post.PostNotFoundException;
import hu.hm.fitjourneyapi.mapper.social.PostMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.services.interfaces.common.FileShareService;
import hu.hm.fitjourneyapi.services.interfaces.social.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final FileShareService fileShareService;
    private final String uploadDir = "uploads/";

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository, FileShareService fileShareService) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.fileShareService = fileShareService;
    }

    @Transactional
    @Override
    public PostDTO getPostById(UUID id, UUID currentUserId) {
        log.debug("Fetching post by id: {}", id);
        Post post = postRepository.findById(id).orElseThrow(()->
        {
            log.warn("Post not found with id: {}", id);
            return new PostNotFoundException("Post not found with id: " + id);
        }
        );
        log.info("Fetched post with id: {} ",id);
        return postMapper.toPostDTO(post, currentUserId);
    }

    @Transactional
    @Override
    public List<PostDTO> getPosts(UUID currentUserId) {
        log.debug("Fetching posts");
        List<Post> posts = postRepository.findAll();
        log.info("Fetched posts");
        return postMapper.toListPostDTO(posts, currentUserId);
    }

    @Transactional
    @Override
    public List<PostDTO> getPostsByUserId(UUID id) {
        log.debug("Fetching posts by user id: {}", id);
        List<Post> posts = postRepository.findPostsByUserId(id);
        log.info("Fetched posts by user id: {} ",id);
        return postMapper.toListPostDTO(posts, id);
    }

    @Transactional
    @Override
    public PostDTO updatePost(UUID id, PostUpdateDTO postUpdateDTO, UUID currentUserId) {
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
        post = postRepository.save(post);
        return postMapper.toPostDTO(post, currentUserId);
    }

    @Transactional
    @Override
    public PostDTO createPost(PostCreateDTO postCreateDTO, UUID currentUserId) {
        log.debug("Attempting to create post");
        User user =userRepository.findById(currentUserId).orElseThrow(
                ()-> {
                    log.warn("User not found with id: {}", currentUserId);
                    return new PostNotFoundException("User not found with id: " + currentUserId);
                }
        );
        Post post = Post.builder()
                .user(user)
                .title(postCreateDTO.getTitle())
                .content(postCreateDTO.getContent())
                .build();
        log.info("Created post with id: {} ",postCreateDTO.getUserId());
        post = postRepository.save(post);
        user.addPost(post);
        return postMapper.toPostDTO(post, currentUserId);
    }

    @Override
    public PostDTO createPostWithImage(PostCreateDTO postCreateDTO, MultipartFile image, UUID currentUserId) {
        String fileName = null;
        if (image != null && !image.isEmpty()) {
            fileName = fileShareService.store(image);
        }

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = Post.builder()
                .title(postCreateDTO.getTitle())
                .content(postCreateDTO.getContent())
                .user(user)
                .imageUrl(fileName)
                .build();

        Post savedPost = postRepository.save(post);
        return postMapper.toPostDTO(savedPost, postCreateDTO.getUserId());
    }

    @Override
    public void likePost(UUID id, UUID userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        Set<UUID> likedByUsers = post.getLikedByUsers();

        if (likedByUsers.contains(userId)) {
            likedByUsers.remove(userId);
            log.debug("User {} unliked post {}", userId, id);
        } else {
            likedByUsers.add(userId);
            log.debug("User {} liked post {}", userId, id);
        }

        postRepository.save(post);
    }

    @Transactional
    @Override
    public void deletePostById(UUID id) {
        log.debug("Attempting to delete post by id: {}", id);
        Post post = postRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("Post not found by id: {}", id);
                    return new PostNotFoundException("Post not found by id: " + id);
                }
        );
        log.info("Deleted post with id: {} ",id);
        postRepository.delete(post);
    }
}