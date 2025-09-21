package hu.hm.fitjourneyapi.repository.social;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findPostsByTitle(String s);

    List<Post> findPostsByUser(User user);
}