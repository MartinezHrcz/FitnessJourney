package hu.hm.fitjourneyapi.service.social;

import hu.hm.fitjourneyapi.dto.social.comment.CommentCreateDTO;
import hu.hm.fitjourneyapi.dto.social.comment.CommentDTO;
import hu.hm.fitjourneyapi.mapper.social.CommentMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Comment;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.CommentRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.services.implementation.social.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentMapper mapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;
    private CommentDTO commentDTO;
    private User user;
    private Post post;
    private UUID commentId;

    @BeforeEach
    void setUp() {
        commentId = UUID.randomUUID();
        user = new User();
        user.setId(UUID.randomUUID());

        post = new Post();
        post.setId(UUID.randomUUID());

        comment = Comment.builder()
                .id(commentId)
                .user(user)
                .post(post)
                .content("Test Comment Content")
                .build();

        commentDTO = new CommentDTO();
        commentDTO.setId(commentId);
        commentDTO.setContent("Test Comment Content");
    }

    @Test
    void getCommentById_Success() {
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(mapper.toCommentDTO(comment)).thenReturn(commentDTO);

        CommentDTO result = commentService.getCommentById(commentId);

        assertNotNull(result);
        assertEquals(commentDTO.getContent(), result.getContent());
        verify(commentRepository).findById(commentId);
    }

    @Test
    void getCommentById_NotFound_ThrowsException() {
        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> commentService.getCommentById(commentId));
    }

    @Test
    void createComment_Success() {
        CommentCreateDTO createDTO = new CommentCreateDTO();
        createDTO.setContent("Test Comment Content");

        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(mapper.toCommentDTO(any(Comment.class))).thenReturn(commentDTO);

        CommentDTO result = commentService.createComment(createDTO, post.getId(), user.getId());

        assertNotNull(result);
        verify(commentRepository).save(any(Comment.class));
        verify(postRepository).findById(post.getId());
        verify(userRepository).findById(user.getId());
    }

    @Test
    void createComment_PostNotFound_ThrowsException() {
        when(postRepository.findById(any())).thenReturn(Optional.empty());
        CommentCreateDTO createDTO = new CommentCreateDTO();
        createDTO.setContent("Test Comment Content");

        assertThrows(NoSuchElementException.class,
                () -> commentService.createComment(createDTO, UUID.randomUUID(), user.getId()));
    }

    @Test
    void updateComment_Success() {
        String newContent = "Updated content";
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO updatedDTO = new CommentDTO();
        updatedDTO.setContent(newContent);
        when(mapper.toCommentDTO(comment)).thenReturn(updatedDTO);

        CommentDTO result = commentService.updateComment(commentId, newContent);

        assertEquals(newContent, result.getContent());
        assertEquals(newContent, comment.getContent());
    }

    @Test
    void getCommentsByPostId_Success() {
        UUID postId = post.getId();
        when(commentRepository.findAllByPost_Id(postId)).thenReturn(List.of(comment));
        when(mapper.toCommentDTOs(anyList())).thenReturn(List.of(commentDTO));

        List<CommentDTO> result = commentService.getCommentsByPostId(postId);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(commentRepository).findAllByPost_Id(postId);
    }

    @Test
    void deleteComment_Success() {
        when(commentRepository.existsById(commentId)).thenReturn(false);

        commentService.deleteComment(commentId);

        verify(commentRepository).deleteById(commentId);
        verify(commentRepository).existsById(commentId);
    }

    @Test
    void deleteComment_Failure_ThrowsIllegalStateException() {
        when(commentRepository.existsById(commentId)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> commentService.deleteComment(commentId));
    }
}