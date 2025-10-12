package hu.hm.fitjourneyapi.service.social;

import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import hu.hm.fitjourneyapi.exception.social.post.PostNotFoundException;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.social.PostMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.PostService;
import hu.hm.fitjourneyapi.utils.PostsTestFactory;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PostServiceTests {

    @Autowired
    private PostService postService;

    @MockitoBean
    private PostRepository postRepository;

    @MockitoBean
    private PostMapper postMapper;

    private User user;
    private Post post;
    private PostDTO postDTO;
    private User userDTO;
    @MockitoBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = UserTestFactory.getUser();
        post = PostsTestFactory.getPost(user);
        postDTO = PostsTestFactory.getPostDTO();

        when(postRepository.save(post)).thenReturn(post);
        when(postRepository.findPostsByUserId(user.getId())).thenReturn(List.of(post));
        when(postRepository.findById(1L)).thenReturn(Optional.ofNullable(post));
        when(postRepository.findAll()).thenReturn(List.of(post));
        when(postMapper.toListPostDTO(List.of(post))).thenReturn(List.of(postDTO));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(userRepository.findById(postDTO.getUserId())).thenReturn(Optional.ofNullable(user));


        when(postMapper.toPostDTO(any(Post.class)))
                .thenAnswer(invocation ->
                        {
                           Post post = invocation.getArgument(0);
                           return PostDTO
                                   .builder()
                                   .title(post.getTitle())
                                   .content(post.getContent())
                                   .userId(post.getUser().getId())
                                   .sentTime(post.getSentTime())
                                   .build();
                        }
                );
    }

    @Test
    public void PostCreateTest_PostCreated_success() {
        PostCreateDTO createDTO = PostCreateDTO
                .builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .userId(1L)
                .build();

        when(userRepository.findById(createDTO.getUserId())).thenReturn(Optional.ofNullable(user));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PostDTO result = postService.createPost(createDTO);

        assertEquals(postDTO.getTitle(), result.getTitle());
        assertEquals(postDTO.getContent(), result.getContent());
        assertEquals(postDTO.getUserId(), result.getUserId());
    }

    @Test
    public void PostCreateTest_UserIdNotFound_fail() {
        when(userRepository.findById(user.getId())).thenThrow(UserNotFound.class);
        PostCreateDTO createDTO = PostCreateDTO
                .builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .userId(1L)
                .build();
        assertThrows(UserNotFound.class,()-> postService.createPost(createDTO));
    }

    @Test
    public void PostUpdateTest_PostUpdated_success() {
        PostUpdateDTO updateDTO = PostUpdateDTO
                .builder()
                .title("Updated title")
                .content("Updated content")
                .build();
        PostDTO result = postService.updatePost(post.getId(), updateDTO);
        assertNotNull(result);
        assertEquals("Updated title", result.getTitle());
        assertEquals("Updated content", result.getContent());
    }

    public void PostUpdateTest_PostIdNotFound_fail() {

        PostUpdateDTO updateDTO = PostUpdateDTO
                .builder()
                .title("Updated title")
                .content("Updated content")
                .build();

        when(postRepository.findById(post.getId())).thenThrow(UserNotFound.class);
        assertThrows(UserNotFound.class,()-> postService.updatePost(post.getId(), updateDTO));

    }

    @Test
    public void PostUpdateTest_PostNotFound_fail() {
        PostUpdateDTO updateDTO = PostUpdateDTO
                .builder()
                .title("Updated title")
                .content("Updated content")
                .build();
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () ->postService.updatePost(post.getId(), updateDTO));
    }

    @Test
    public void PostDeleteTest_PostDeleted_success() {
        postService.deletePostById(post.getId());
        verify(postRepository,times(1)).delete(any(Post.class));
    }

    @Test
    public void PostDeleteTest_PostNotFound_fail() {
        when(postRepository.findById(post.getId())).thenThrow(PostNotFoundException.class);
        assertThrows(PostNotFoundException.class, () ->postService.deletePostById(post.getId()));
    }

    @Test
    public void GetPostByIdTest_PostFound_success() {

    }

    @Test
    public void GetPostByUserIdTest_success() {

    }

    @Test
    public void GetPostByIdTest_PostNotFound_fail() {

    }

    @Test
    public void GetPostByUserId_UserIdNotFound_fail() {

    }

    @Test
    public void GetPosts_success(){

    }

}
