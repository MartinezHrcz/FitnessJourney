package hu.hm.fitjourneyapi.service.social;

import hu.hm.fitjourneyapi.dto.social.post.PostCreateDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostUpdateDTO;
import hu.hm.fitjourneyapi.exception.social.post.PostNotFoundException;
import hu.hm.fitjourneyapi.mapper.social.PostMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.services.implementation.social.PostServiceImpl;
import hu.hm.fitjourneyapi.services.interfaces.common.FileShareService;
import hu.hm.fitjourneyapi.utils.PostsTestFactory;
import hu.hm.fitjourneyapi.utils.UserTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PostServiceTests {
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FileShareService fileShareService;

    @InjectMocks
    private PostServiceImpl postService;

    private User user;
    private Post post;
    private PostDTO postDTO;
    private UUID currentUserId;

    @BeforeEach
    void setUp() {
        currentUserId = UUID.randomUUID();
        user = UserTestFactory.getUser();
        user.setId(currentUserId);

        post = PostsTestFactory.getPost(user);
        post.setId(UUID.randomUUID());

        postDTO = PostsTestFactory.getPostDTO();
        postDTO.setUserId(currentUserId);
    }

    @Test
    void createPost_Success() {
        PostCreateDTO createDTO = new PostCreateDTO("Title", "Content", currentUserId);

        when(userRepository.findById(currentUserId)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.toPostDTO(any(Post.class), eq(currentUserId))).thenReturn(postDTO);

        PostDTO result = postService.createPost(createDTO, currentUserId);

        assertNotNull(result);
        verify(postRepository).save(any(Post.class));
        verify(userRepository).findById(currentUserId);
    }

    @Test
    void createPostWithImage_Success() {
        PostCreateDTO createDTO = new PostCreateDTO("Title", "Content", currentUserId);
        MultipartFile mockFile = mock(MultipartFile.class);

        when(mockFile.isEmpty()).thenReturn(false);
        when(fileShareService.store(mockFile)).thenReturn("image_path.jpg");
        when(userRepository.findById(currentUserId)).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(postMapper.toPostDTO(any(Post.class), eq(currentUserId))).thenReturn(postDTO);

        PostDTO result = postService.createPostWithImage(createDTO, mockFile, currentUserId);

        assertNotNull(result);
        verify(fileShareService).store(mockFile);
    }

    @Test
    void updatePost_Success() {
        PostUpdateDTO updateDTO = PostUpdateDTO.builder().title("Updated Title").content("Updated content").build();

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDTO updatedDTO = PostDTO.builder()
                .title(updateDTO.getTitle())
                .content(updateDTO.getContent())
                .build();
        when(postMapper.toPostDTO(post, currentUserId)).thenReturn(updatedDTO);

        PostDTO result = postService.updatePost(post.getId(), updateDTO, currentUserId);

        assertEquals("Updated Title", result.getTitle());
        verify(postRepository).save(post);
    }

    @Test
    void likePost_ToggleBehavior() {
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        postService.likePost(post.getId(), currentUserId);
        assertTrue(post.getLikedByUsers().contains(currentUserId));

        postService.likePost(post.getId(), currentUserId);
        assertFalse(post.getLikedByUsers().contains(currentUserId));

        verify(postRepository, times(2)).save(post);
    }

    @Test
    void getPostsByUserId_Success() {
        when(postRepository.findPostsByUserId(currentUserId)).thenReturn(List.of(post));
        when(postMapper.toListPostDTO(anyList(), eq(currentUserId))).thenReturn(List.of(postDTO));

        List<PostDTO> result = postService.getPostsByUserId(currentUserId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void deletePost_NotFound_ThrowsException() {
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(PostNotFoundException.class, () -> postService.deletePostById(UUID.randomUUID()));
    }
}
