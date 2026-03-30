package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import hu.hm.fitjourneyapi.exception.common.UnauthorizedException;
import hu.hm.fitjourneyapi.exception.social.post.PostNotFoundException;
import hu.hm.fitjourneyapi.mapper.social.PostMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.PostVisibility;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import hu.hm.fitjourneyapi.model.social.Friend;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.FriendRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.services.interfaces.common.FileShareService;
import hu.hm.fitjourneyapi.services.interfaces.social.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Objects;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final FileShareService fileShareService;
    private final FriendRepository friendRepository;
    private final String uploadDir = "uploads/";

    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository, FileShareService fileShareService, FriendRepository friendRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.fileShareService = fileShareService;
        this.friendRepository = friendRepository;
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
        if (!canUserViewPost(post, currentUserId)) {
            throw new UnauthorizedException("You are not allowed to view this post");
        }
        log.info("Fetched post with id: {} ",id);
        return postMapper.toPostDTO(post, currentUserId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDTO> getPosts(UUID currentUserId) {
        log.debug("Fetching posts");
        List<Post> posts = new ArrayList<>(postRepository.findAll());
        posts.sort(Comparator.comparing(Post::getSentTime,
                Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        log.info("Fetched posts");
        return postMapper.toListPostDTO(posts, currentUserId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDTO> getFeedPosts(UUID currentUserId) {
        log.debug("Fetching feed posts for user: {}", currentUserId);
        List<Post> posts = postRepository.findAll().stream()
                .filter(post -> canUserViewPost(post, currentUserId))
            .collect(Collectors.toCollection(ArrayList::new));
        posts.sort(Comparator.comparing(Post::getSentTime,
                Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        log.info("Fetched {} feed posts for user: {}", posts.size(), currentUserId);
        return postMapper.toListPostDTO(posts, currentUserId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDTO> getFriendsPosts(UUID currentUserId) {
        log.debug("Fetching friends posts for user: {}", currentUserId);
        List<Friend> friendships = friendRepository.findFriendsByUser_IdOrFriend_Id(currentUserId, currentUserId);
        List<UUID> friendIds = friendships.stream()
                .filter(f -> f.getStatus() == FriendStatus.ACCEPTED)
                .map(f -> f.getUser().getId().equals(currentUserId) ? f.getFriend().getId() : f.getUser().getId())
                .collect(Collectors.toList());
        List<Post> posts = postRepository.findPostsByUserIdIn(friendIds).stream()
            .filter(post -> canUserViewPost(post, currentUserId))
            .collect(Collectors.toCollection(ArrayList::new));
        posts.sort(Comparator.comparing(Post::getSentTime,
            Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        log.info("Fetched {} friends posts for user: {}", posts.size(), currentUserId);
        return postMapper.toListPostDTO(posts, currentUserId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostDTO> getPostsByUserId(UUID id) {
        log.debug("Fetching posts by user id: {}", id);
        List<Post> posts = new ArrayList<>(postRepository.findPostsByUserId(id));
        posts.sort(Comparator.comparing(Post::getSentTime,
                Comparator.nullsLast(Comparator.naturalOrder())).reversed());
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
        if (postUpdateDTO.getVisibility() != null) {
            post.setVisibility(postUpdateDTO.getVisibility());
        }

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
            .visibility(resolveVisibility(postCreateDTO.getVisibility()))
                .build();
        log.info("Created post with id: {} ",postCreateDTO.getUserId());
        post = postRepository.save(post);
        user.addPost(post);
        return postMapper.toPostDTO(post, currentUserId);
    }

    @Transactional
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
            .visibility(resolveVisibility(postCreateDTO.getVisibility()))
                .build();

        Post savedPost = postRepository.save(post);
        return postMapper.toPostDTO(savedPost, postCreateDTO.getUserId());
    }

    @Transactional
    @Override
    public void likePost(UUID id, UUID userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        Set<UUID> likedByUsers = post.getLikedByUsers();

        if (!likedByUsers.add(userId)) {
            likedByUsers.remove(userId);
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

    private PostVisibility resolveVisibility(PostVisibility visibility) {
        return visibility != null ? visibility : PostVisibility.GLOBAL;
    }

    private boolean canUserViewPost(Post post, UUID viewerId) {
        if (post == null || post.getUser() == null || post.getUser().getId() == null || viewerId == null) {
            return false;
        }
        if (Objects.equals(post.getUser().getId(), viewerId)) {
            return true;
        }

        PostVisibility visibility = resolveVisibility(post.getVisibility());
        if (visibility == PostVisibility.GLOBAL) {
            return true;
        }

        return areAcceptedFriends(post.getUser().getId(), viewerId);
    }

    private boolean areAcceptedFriends(UUID firstUserId, UUID secondUserId) {
        return friendRepository.findFriendsByUser_IdOrFriend_Id(firstUserId, firstUserId).stream()
                .filter(friend -> friend.getStatus() == FriendStatus.ACCEPTED)
                .anyMatch(friend -> {
                    UUID requesterId = friend.getUser().getId();
                    UUID recipientId = friend.getFriend().getId();
                    return (Objects.equals(requesterId, firstUserId) && Objects.equals(recipientId, secondUserId))
                            || (Objects.equals(requesterId, secondUserId) && Objects.equals(recipientId, firstUserId));
                });
    }
}