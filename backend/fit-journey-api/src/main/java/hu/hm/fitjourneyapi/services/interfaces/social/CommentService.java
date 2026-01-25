package hu.hm.fitjourneyapi.services.interfaces.social;

import hu.hm.fitjourneyapi.dto.social.comment.CommentCreateDTO;
import hu.hm.fitjourneyapi.dto.social.comment.CommentDTO;
import hu.hm.fitjourneyapi.dto.social.message.CreateMessageDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentDTO getCommentById(UUID id);

    List<CommentDTO> getCommentsByPostId(UUID id);

    List<CommentDTO> getCommentsByUserId(UUID id);

    List<CommentDTO> getCommentsByPostIdAndUserId(UUID id, UUID userId);

    List<CommentDTO> getComments();

    CommentDTO createComment(CommentCreateDTO commentDTO, UUID postId, UUID userId);

    CommentDTO updateComment(UUID id, String comment);

    void deleteComment(UUID id);
}
