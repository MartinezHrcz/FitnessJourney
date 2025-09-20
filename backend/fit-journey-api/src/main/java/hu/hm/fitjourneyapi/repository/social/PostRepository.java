package hu.hm.fitjourneyapi.repository.social;

import hu.hm.fitjourneyapi.model.social.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}