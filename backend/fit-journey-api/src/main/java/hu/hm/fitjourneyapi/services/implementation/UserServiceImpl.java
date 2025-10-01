package hu.hm.fitjourneyapi.services.implementation;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserPasswordUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.fitness.UserWithWorkoutsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithFriendsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithPostsDTO;
import hu.hm.fitjourneyapi.exception.fitness.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = userMapper.toUser(userCreateDTO);
        user = userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(UserUpdateDTO userUpdateDTO) {
        User userToUpdate = userRepository.findById(userUpdateDTO.getId()).orElseThrow(
                () -> new UserNotFound("User not found with id:" + userUpdateDTO.getId())
        );
        userToUpdate.setName(userUpdateDTO.getName());
        userToUpdate.setEmail(userUpdateDTO.getEmail());
        userToUpdate.setBirthday(userUpdateDTO.getBirthday());
        userToUpdate.setHeightInCm(userUpdateDTO.getHeightInCm());
        userToUpdate.setWeightInKg(userUpdateDTO.getWeightInKg());

        userToUpdate = userRepository.save(userToUpdate);
        return userMapper.toUserDTO(userToUpdate);
    }

    @Override
    public UserDTO updatePassword(UserPasswordUpdateDTO userPasswordUpdateDTO) {
        return null;
    }

    @Override
    public UserDTO getUserById(long id) {
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFound("User not found with id: " + id));
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFound("User not found with email: " + email)
        );
        return userMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserDTOList(users);
    }

    @Override
    public List<UserDTO> getUsersByName(String name) {
        List<User> users = userRepository.findUsersByName(name);
        return userMapper.toUserDTOList(users);
    }

    @Override
    public UserWithWorkoutsDTO getUserWithWorkoutsById(long id) {
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFound("User not found with id: " + id)
        );
        return userMapper.toUserWithWorkoutsDTO(user);
    }

    @Override
    public UserWithFriendsDTO getUserWithFriendsById(long id) {
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFound("User not found with id: " + id)
        );
        return userMapper.toUserWithFriendsDTO(user);
    }

    @Override
    public UserWithPostsDTO getUserWithPostsById(long id) {
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFound("User not found with id: " + id)
        );
        return userMapper.toUserWithPostsDTO(user);
    }

    @Override
    public void deleteUser(long id) {

    }
}
