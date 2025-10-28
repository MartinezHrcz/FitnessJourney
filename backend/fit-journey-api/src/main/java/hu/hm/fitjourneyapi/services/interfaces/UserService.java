package hu.hm.fitjourneyapi.services.interfaces;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserPasswordUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.fitness.UserWithWorkoutsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithFriendsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithPostsDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserCreateDTO userCreateDTO);

    UserDTO updateUser(UserUpdateDTO userUpdateDTO);

    UserDTO updatePassword(UserPasswordUpdateDTO userPasswordUpdateDTO);

    UserDTO getUserById(long id);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    UserDTO getUsersByName(String name);


    UserWithWorkoutsDTO getUserWithWorkoutsById(long id);

    UserWithFriendsDTO getUserWithFriendsById(long id);

    UserWithPostsDTO getUserWithPostsById(long id);

    void deleteUserPost(PostDTO postDTO);

    void deleteUserFriends(FriendDTO friendDTO);

    void deleteUserWorkout(WorkoutDTO workoutDTO);

    void deleteUser(long id);

}
