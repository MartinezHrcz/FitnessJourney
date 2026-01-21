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
import hu.hm.fitjourneyapi.services.interfaces.social.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final String uploadDir = "uploads/";

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public PostDTO getPostById(UUID id) {
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

    @Transactional
    @Override
    public List<PostDTO> getPosts() {
        log.debug("Fetching posts");
        List<Post> posts = postRepository.findAll();
        log.info("Fetched posts");
        return postMapper.toListPostDTO(posts);
    }

    @Transactional
    @Override
    public List<PostDTO> getPostsByUserId(UUID id) {
        log.debug("Fetching posts by user id: {}", id);
        List<Post> posts = postRepository.findPostsByUserId(id);
        log.info("Fetched posts by user id: {} ",id);
        return postMapper.toListPostDTO(posts);
    }

    @Transactional
    @Override
    public PostDTO updatePost(UUID id, PostUpdateDTO postUpdateDTO) {
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
        return postMapper.toPostDTO(post);
    }

    @Transactional
    @Override
    public PostDTO createPost(PostCreateDTO postCreateDTO) {
        log.debug("Attempting to create post");
        User user =userRepository.findById(postCreateDTO.getUserId()).orElseThrow(
                ()-> {
                    log.warn("User not found with id: {}", postCreateDTO.getUserId());
                    return new PostNotFoundException("User not found with id: " + postCreateDTO.getUserId());
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
        return postMapper.toPostDTO(post);
    }

    @Override
    public PostDTO createPostWithImage(PostCreateDTO postCreateDTO, MultipartFile image) {
        String fileName = null;

        if (image != null && image.isEmpty()) {
            fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            try {
                Files.createDirectory(path.getParent());
                Files.write(path, image.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Post post = Post.builder()
                .content(postCreateDTO.getContent())
                .user(userRepository.findById(postCreateDTO.getUserId()).orElseThrow())
                .imageUrl(fileName)
                .build();

        return postMapper.toPostDTO(post);
    }

    @Override
    public void likePost(UUID id, UUID userId) {

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