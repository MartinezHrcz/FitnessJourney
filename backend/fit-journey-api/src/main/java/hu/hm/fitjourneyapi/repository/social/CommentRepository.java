package hu.hm.fitjourneyapi.repository.social;

import hu.hm.fitjourneyapi.model.social.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByPost_Id(UUID postId);

    List<Comment> findAllByUser_Id(UUID userId);

    List<Comment> findAllByPost_Id_AndUser_Id(UUID postId, UUID userId);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.user = null WHERE c.user.id = :userId")
    void nullifyUserComments(@Param("userId") UUID userId);
}
