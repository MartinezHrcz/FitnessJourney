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

import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTests {

    @Autowired
    private PostService postService;

    @MockitoBean
    private PostRepository repository;

    @MockitoBean
    private PostMapper postMapper;

    private User user;
    private Post post;
    private PostDTO postDTO;
    private User userDTO;
    @MockitoBean
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        user = UserTestFactory.getUser();
        post = PostsTestFactory.getPost(user);
        postDTO = PostsTestFactory.getPostDTO();

        when(repository.save(post)).thenReturn(post);
        when(repository.findPostsByUserId(user.getId())).thenReturn(List.of(post));
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(post));
        when(repository.findAll()).thenReturn(List.of(post));
        when(postMapper.toListPostDTO(List.of(post))).thenReturn(List.of(postDTO));
        when(repository.findById(post.getId())).thenReturn(Optional.of(post));
        when(userRepository.findById(postDTO.getUserId())).thenReturn(Optional.ofNullable(user));


        when(postMapper.toPostDTO(post))
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
        PostDTO result = postService.createPost(createDTO);
        assertNotNull(result);
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

}
