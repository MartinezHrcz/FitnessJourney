package hu.hm.fitjourneyapi.services.implementation.social;

import hu.hm.fitjourneyapi.dto.social.comment.CommentCreateDTO;
import hu.hm.fitjourneyapi.dto.social.comment.CommentDTO;
import hu.hm.fitjourneyapi.dto.social.message.CreateMessageDTO;
import hu.hm.fitjourneyapi.mapper.social.CommentMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Comment;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.CommentRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.services.interfaces.social.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository, CommentMapper mapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO getCommentById(UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> {
                    log.warn("Comment with id {} not found", id);
                    return new NoSuchElementException("Comment with id " + id + " not found");
                }
        );

        return mapper.toCommentDTO(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(UUID id) {
        List<Comment> comment = commentRepository.findAllByPost_Id(id);

        return mapper.toCommentDTOs(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByUserId(UUID id) {
        List<Comment> comment = commentRepository.findAllByUser_Id(id);

        return mapper.toCommentDTOs(comment);    }

    @Override
    public List<CommentDTO> getCommentsByPostIdAndUserId(UUID postId, UUID userId) {
        List<Comment> comment = commentRepository.findAllByPost_Id_AndUser_Id(postId,userId);

        return mapper.toCommentDTOs(comment);
    }

    @Override
    public List<CommentDTO> getComments() {
        List<Comment> comments = commentRepository.findAll();

        return mapper.toCommentDTOs(comments);
    }

    @Override
    public CommentDTO createComment(CommentCreateDTO commentDTO, UUID postId, UUID userId) {
        log.debug("Creating new comment for post {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> {
                    log.warn("Post with id {} not found", postId);
                    return new NoSuchElementException("Post with id "+ postId+" not found");
                }
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    log.warn("Post with id {} not found", postId);
                    return new NoSuchElementException("Post with id "+ postId+" not found");
                }
        );

        Comment comment = mapper.toComment(commentDTO.getContent(), post, user);

        log.info("Created comment with id {} ", comment.getId());
        return  mapper.toCommentDTO(commentRepository.save(comment));
    }

    @Override
    public CommentDTO updateComment(UUID id, CreateMessageDTO commentDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("Comment with id {} not found", id);
                    return new NoSuchElementException("Comment with id "+ id +" not found");
                }
        );

        comment.setContent(commentDTO.getContent());

        comment = commentRepository.save(comment);

        return mapper.toCommentDTO(comment);
    }

    @Override
    public void deleteComment(UUID id) {
        log.debug("Deleting comment with id {}", id);
        commentRepository.deleteById(id);
        if (commentRepository.existsById(id))
        {
            throw new IllegalStateException("Comment with id " + id + " was not deleted");
        }
    }
}
