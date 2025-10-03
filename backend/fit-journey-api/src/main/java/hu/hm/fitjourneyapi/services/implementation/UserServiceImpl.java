package hu.hm.fitjourneyapi.services.implementation;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserPasswordUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.fitness.UserWithWorkoutsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithFriendsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithPostsDTO;
import hu.hm.fitjourneyapi.exception.userExceptions.IncorrectPassword;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        log.debug("Attempting to create a new user with name {} email {}", userCreateDTO.getName(), userCreateDTO.getEmail());
        User user = userMapper.toUser(userCreateDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        log.info("Created new user with id: " + user.getId());
        return userMapper.toUserDTO(user);
    }

    @Transactional
    @Override
    public UserDTO updateUser(UserUpdateDTO userUpdateDTO) {
        log.debug("Attempting to update user with id {} email {}", userUpdateDTO.getId(), userUpdateDTO.getName());
        User userToUpdate = userRepository.findById(userUpdateDTO.getId()).orElseThrow(
                () -> new UserNotFound("User not found with id:" + userUpdateDTO.getId())
        );
        userToUpdate.setName(userUpdateDTO.getName());
        userToUpdate.setEmail(userUpdateDTO.getEmail());
        userToUpdate.setBirthday(userUpdateDTO.getBirthday());
        userToUpdate.setHeightInCm(userUpdateDTO.getHeightInCm());
        userToUpdate.setWeightInKg(userUpdateDTO.getWeightInKg());

        userToUpdate = userRepository.save(userToUpdate);
        log.info("Updated user with id: " + userToUpdate.getId());
        return userMapper.toUserDTO(userToUpdate);
    }

    @Transactional
    @Override
    public UserDTO updatePassword(UserPasswordUpdateDTO userPasswordUpdateDTO) {
        log.debug("Attempting to update user password with id {} ", userPasswordUpdateDTO.getId());
        User userToUpdate = userRepository.findById(userPasswordUpdateDTO.getId()).orElseThrow(
                () -> new UserNotFound("User not found with id:" + userPasswordUpdateDTO.getId())
        );

        if (!passwordEncoder.matches(userToUpdate.getPassword(), userPasswordUpdateDTO.getPasswordOld())){
            throw new IncorrectPassword("Old password doesn't match");
        }

        userToUpdate.setPassword(passwordEncoder.encode(userPasswordUpdateDTO.getPasswordNew()));
        userToUpdate = userRepository.save(userToUpdate);
        log.info("Updated password for user with id: " + userToUpdate.getId());
        return userMapper.toUserDTO(userToUpdate);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserById(long id) {
        log.debug("Fetching user with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFound("User not found with id: " + id));
        log.debug("Fetched user with id: " + user.getId());
        return userMapper.toUserDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserByEmail(String email) {
        log.debug("Fetching user with email {} ", email);
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserNotFound("User not found with email: " + email)
        );
        log.debug("Fetched user with email: " + user.getId());
        return userMapper.toUserDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> getAllUsers() {
        log.debug("Fetching all users");
        List<User> users = userRepository.findAll();
        log.debug("Fetched all users");
        return userMapper.toUserDTOList(users);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> getUsersByName(String name) {
        log.debug("Fetching all users with name {} ", name);
        List<User> users = userRepository.findUsersByName(name);
        log.debug("Fetched all users with name {} ", name);
        return userMapper.toUserDTOList(users);
    }

    @Transactional(readOnly = true)
    @Override
    public UserWithWorkoutsDTO getUserWithWorkoutsById(long id) {
        log.debug("Fetching user with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFound("User not found with id: " + id)
        );
        log.debug("Fetched user-workoutList with id {} ", id);
        return userMapper.toUserWithWorkoutsDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserWithFriendsDTO getUserWithFriendsById(long id) {
        log.debug("Fetching user-friendList with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFound("User not found with id: " + id)
        );
        log.debug("Fetched user-friendList with id {} ", id);
        return userMapper.toUserWithFriendsDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserWithPostsDTO getUserWithPostsById(long id) {
        log.debug("Fetching user-posts with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFound("User not found with id: " + id)
        );
        log.debug("Fetched user-posts with id {} ", id);
        return userMapper.toUserWithPostsDTO(user);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        log.debug("Deleting user with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFound("User not found with id: " + id)
        );
        log.info("Deleted user with id: {} ",id);
        userRepository.delete(user);
    }
}
