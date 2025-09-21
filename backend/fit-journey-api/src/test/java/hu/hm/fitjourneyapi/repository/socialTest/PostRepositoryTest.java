package hu.hm.fitjourneyapi.repository.socialTest;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.repository.testutil.TestSocialDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(value = "hu.hm.fitjourneyapi.repository.testutil",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class))
public class PostRepositoryTest {

    @Autowired
    private TestSocialDataFactory factory;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Post post;

    @BeforeEach
    void setup() {
        user = factory.createUser();
        post = factory.createPost(user);
    }

    @Test
    void testSavePost(){
        assertTrue(postRepository.findById(post.getId()).isPresent());
    }

    @Test
    void testFindPostById(){
        assertTrue(postRepository.findById(post.getId()).isPresent());
    }

    @Test
    void testUpdatePost(){
        post.setTitle("Updated title");
        post.setContent("Updated content");
        postRepository.save(post);
        assertEquals(post, postRepository.findById(post.getId()).get());

    }

    @Test
    void testFindPostByTitle(){
        assertTrue(postRepository.findPostsByTitle(post.getTitle()).contains(post));
    }

    @Test
    void testFindPostsBySender(){
        assertTrue(userRepository.findById(user.getId()).isPresent());
        List<Post> userPosts = postRepository.findPostsByUser(user);
        System.out.println(user.getPosts());
        assertTrue(userPosts.contains(post));
    }

    @Test
    void testDeletePost(){
        postRepository.deleteById(post.getId());
        assertFalse(postRepository.findById(post.getId()).isPresent());
    }

}
