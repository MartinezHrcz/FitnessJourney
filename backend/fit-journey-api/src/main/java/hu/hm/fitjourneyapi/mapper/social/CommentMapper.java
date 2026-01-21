package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.comment.CommentDTO;
import hu.hm.fitjourneyapi.model.social.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "sentTime", target = "sentTime")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "user.profileImageUrl", target = "userProfilePicture")
    CommentDTO toCommentDTO(Comment comment);

    List<CommentDTO> toCommentDTOs(List<Comment> comments);
}
