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
import java.util.UUID;

public interface UserService {

    UserDTO createUser(UserCreateDTO userCreateDTO);

    UserDTO updateUser(UUID id,UserUpdateDTO userUpdateDTO);

    UserDTO updatePassword(UUID id,UserPasswordUpdateDTO userPasswordUpdateDTO);

    UserDTO getUserById(UUID id);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    UserDTO getUserByName(String name);

    List<UserDTO> getAllUsersByName(String name);

    UserWithWorkoutsDTO getUserWithWorkoutsById(UUID id);

    UserWithFriendsDTO getUserWithFriendsById(UUID id);

    UserWithPostsDTO getUserWithPostsById(UUID id);

    void deleteUserPost(PostDTO postDTO);

    void deleteUserFriends(FriendDTO friendDTO);

    void deleteUserWorkout(WorkoutDTO workoutDTO);

    void deleteUser(UUID id);

}
