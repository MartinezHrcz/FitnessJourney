package hu.hm.fitjourneyapi.repository.testutil;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.FriendStatus;
import hu.hm.fitjourneyapi.model.social.Friend;
import hu.hm.fitjourneyapi.model.social.Message;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.social.FriendRepository;
import hu.hm.fitjourneyapi.repository.social.MessageRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestSocialDataFactory {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    MessageRepository messageRepository;

    public User createUser(String name) {
        User user = User.builder()
                .name("Placeholder" + name)
                .email("Placeholder@email.com")
                .birthday(LocalDate.of(2000, 1,1))
                .password("PlaceholderPassword")
                .weightInKg(100)
                .heightInCm(180)
                .build();
        userRepository.save(user);
        return user;
    }

    public Post createPost(User user) {
        Post post = Post.builder()
                .title("Placeholder Post")
                .content("This is a placeholder post")
                .user(user).build();
        user.getPosts().add(post);
        userRepository.save(user);
        postRepository.save(post);
        return post;
    }

    public Friend createFriend(User user1, User user2) {
        Friend friend = Friend.builder()
                .user(user1)
                .friend(user2)
                .build();
        user1.getFriends().add(friend);
        user2.getFriends().add(friend);
        friendRepository.save(friend);
        return friend;
    }

    public Message createMessage(User user1, User user2) {
        Message message = Message.builder()
                .content("Placeholder")
                .sender(user1)
                .recipient(user2)
                .build();
        messageRepository.save(message);
        return message;
    }
}
