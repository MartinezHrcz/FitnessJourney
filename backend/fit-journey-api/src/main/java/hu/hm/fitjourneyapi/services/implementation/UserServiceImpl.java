package hu.hm.fitjourneyapi.services.implementation;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserPasswordUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.fitness.UserWithWorkoutsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithFriendsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithPostsDTO;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        return null;
    }

    @Override
    public UserDTO updateUser(UserUpdateDTO userUpdateDTO) {
        return null;
    }

    @Override
    public UserDTO updatePassword(UserPasswordUpdateDTO userPasswordUpdateDTO) {
        return null;
    }

    @Override
    public UserDTO getUserById(long id) {
        return null;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public List<UserDTO> getUsersByName(String name) {
        return List.of();
    }

    @Override
    public UserWithWorkoutsDTO getUserWithWorkoutsById(long id) {
        return null;
    }

    @Override
    public UserWithFriendsDTO getUserWithFriendsById(long id) {
        return null;
    }

    @Override
    public UserWithPostsDTO getUserWithPostsById(long id) {
        return null;
    }

    @Override
    public void deleteUser(long id) {

    }
}
