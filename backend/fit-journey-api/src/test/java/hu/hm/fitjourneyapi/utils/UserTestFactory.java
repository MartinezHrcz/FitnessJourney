package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.fitness.UserWithWorkoutsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithFriendsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithPostsDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.Roles;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.model.social.Friend;
import hu.hm.fitjourneyapi.model.social.Post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserTestFactory {
    public static UserCreateDTO getUserCreateDTO() {
        UserCreateDTO dto = UserCreateDTO.builder()
                .name("Test name")
                .email("user@gmail.com")
                .heightInCm(180)
                .weightInKg(100)
                .password("ExamplePassword123!")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
        return dto;
    }

    public static UserDTO getUserDTO() {
        UserDTO dto = UserDTO.builder()
                .id(UUID.randomUUID())
                .name("Test name")
                .email("user@gmail.com")
                .heightInCm(180)
                .weightInKg(100)
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
        return dto;
    }

    public static List<User> getMultipleUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .id(UUID.randomUUID())
                    .name("Test name")
                    .email("user@gmail.com")
                    .heightInCm(180)
                    .weightInKg(100)
                    .birthday(LocalDate.of(1990, 1, 1))
                    .password("EncodedPassword123!")
                    .role(Roles.USER)
                    .build();
            users.add(user);
        }
        return users;
    }

    public static User getUser() {

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Test name")
                .email("user@gmail.com")
                .heightInCm(180)
                .weightInKg(100)
                .password("EncodedPassword123!")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
        List<Workout> workouts = new ArrayList<>();
        List<Friend> friends = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        workouts.add(WorkoutTestFactory.getWorkout(user));
        friends.add(FriendsTestFactory.getFriend(user));
        posts.add(PostsTestFactory.getPost(user));

        user.setWorkouts(workouts);
        user.setFriends(friends);
        user.setPosts(posts);

        return user;
    }

    public static UserWithWorkoutsDTO getUserWithWorkoutsDTO() {
        UUID id = UUID.randomUUID();
        UserWithWorkoutsDTO dto =
                UserWithWorkoutsDTO.builder()
                        .id(UUID.randomUUID())
                        .name("Test name")
                        .email("user@gmail.com")
                        .heightInCm(180)
                        .weightInKg(100)
                        .birthday(LocalDate.of(1990, 1, 1))
                        .workouts(List.of(WorkoutTestFactory.getWorkoutDTO(id)))
                        .build();
        return dto;
    }

    public static UserWithFriendsDTO getUserWithFriendsDTO() {
        UserWithFriendsDTO dto =
                UserWithFriendsDTO.builder()
                        .id(UUID.randomUUID())
                        .name("Test name")
                        .email("user@gmail.com")
                        .heightInCm(180)
                        .weightInKg(100)
                        .birthday(LocalDate.of(1990, 1, 1))
                        .friends(List.of(FriendsTestFactory.getFriendDTO()))
                        .build();
        return dto;
    }

    public static UserWithPostsDTO getUserWithPostsDTO() {
        UserWithPostsDTO dto = UserWithPostsDTO
                .builder()
                .id(UUID.randomUUID())
                .name("Test name")
                .email("user@gmail.com")
                .heightInCm(180)
                .weightInKg(100)
                .birthday(LocalDate.of(1990, 1, 1))
                .posts(List.of(PostsTestFactory.getPostDTO()))
                .build();
        return dto;
    }
}
