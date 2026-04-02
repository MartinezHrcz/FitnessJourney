package hu.hm.fitjourneyapi.services.implementation;

import hu.hm.fitjourneyapi.dto.fitness.workout.WorkoutDTO;
import hu.hm.fitjourneyapi.dto.social.friend.FriendDTO;
import hu.hm.fitjourneyapi.dto.social.post.PostDTO;
import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserPasswordUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserProfilePictureDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.fitness.UserWithWorkoutsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithFriendsDTO;
import hu.hm.fitjourneyapi.dto.user.social.UserWithPostsDTO;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.exception.social.friend.FriendNotFoundException;
import hu.hm.fitjourneyapi.exception.social.post.PostNotFoundException;
import hu.hm.fitjourneyapi.exception.userExceptions.IncorrectPassword;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.UserMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.model.social.Friend;
import hu.hm.fitjourneyapi.model.social.Post;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.repository.social.CommentRepository;
import hu.hm.fitjourneyapi.repository.social.FriendRepository;
import hu.hm.fitjourneyapi.repository.social.PostRepository;
import hu.hm.fitjourneyapi.security.JwtUtil;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final int MAX_PROFILE_PICTURE_DIMENSION = 512;
    private static final float JPEG_COMPRESSION_QUALITY = 0.82f;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final FriendRepository friendRepository;
    private final WorkoutRepository workoutRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            PostRepository postRepository,
            FriendRepository friendRepository,
            WorkoutRepository workoutRepository,
            CommentRepository commentRepository,
            JwtUtil jwtUtil)
    {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.postRepository = postRepository;
        this.friendRepository = friendRepository;
        this.workoutRepository = workoutRepository;
        this.commentRepository = commentRepository;
        this.jwtUtil = jwtUtil;
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
    public UserDTO updateUser(UUID id,UserUpdateDTO userUpdateDTO) {
        if (userUpdateDTO == null) {
            throw new IllegalArgumentException("User update payload is required");
        }
        log.debug("Attempting to update user with id {} email {}", id, userUpdateDTO.getName());

        User userToUpdate = userRepository.findById(id).orElseThrow(
                () ->{
                    log.warn("User not found with id: {}", id);
                    return new UserNotFound("User not found with id: "+ id);}
        );

        String normalizedName = normalize(userUpdateDTO.getName());
        String normalizedEmail = normalize(userUpdateDTO.getEmail());

        if (normalizedName != null) {
            if (normalizedName.isBlank()) {
                throw new IllegalArgumentException("Name cannot be blank");
            }
            ensureNameNotTakenByAnotherUser(id, normalizedName);
            userToUpdate.setName(normalizedName);
        }

        if (normalizedEmail != null) {
            if (normalizedEmail.isBlank()) {
                throw new IllegalArgumentException("Email cannot be blank");
            }
            ensureEmailNotTakenByAnotherUser(id, normalizedEmail);
            userToUpdate.setEmail(normalizedEmail);
        }

        if (userUpdateDTO.getBirthday() != null) {
            userToUpdate.setBirthday(userUpdateDTO.getBirthday());
        }
        if (userUpdateDTO.getHeightInCm() != null) {
            userToUpdate.setHeightInCm(userUpdateDTO.getHeightInCm());
        }
        if (userUpdateDTO.getWeightInKg() != null) {
            userToUpdate.setWeightInKg(userUpdateDTO.getWeightInKg());
        }
        if (userUpdateDTO.getPreferredCalories() != null) {
            userToUpdate.setPreferredCalories(userUpdateDTO.getPreferredCalories());
        }
        if (userUpdateDTO.getProfilePictureUrl() != null) {
            userToUpdate.setProfilePictureUrl(normalize(userUpdateDTO.getProfilePictureUrl()));
        }
        userToUpdate = userRepository.save(userToUpdate);
        UserDTO userDTO = userMapper.toUserDTO(userToUpdate);
        userDTO.setToken(jwtUtil.generateToken(userToUpdate.getId(),userToUpdate.getName()));
        log.info("Updated user with id: " + id);
        return userDTO;
    }

    private void ensureNameNotTakenByAnotherUser(UUID currentUserId, String name) {
        Optional<User> existingByName = Optional.ofNullable(userRepository.findUserByName(name))
                .orElse(Optional.empty());

        existingByName
                .filter(existing -> !Objects.equals(existing.getId(), currentUserId))
                .ifPresent(existing -> {
                    throw new IllegalStateException("Username is already taken");
                });
    }

    private void ensureEmailNotTakenByAnotherUser(UUID currentUserId, String email) {
        Optional<User> existingByEmail = Optional.ofNullable(userRepository.findUserByEmail(email))
                .orElse(Optional.empty());

        existingByEmail
                .filter(existing -> !Objects.equals(existing.getId(), currentUserId))
                .ifPresent(existing -> {
                    throw new IllegalStateException("Email is already in use");
                });
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }

    @Transactional
    @Override
    public UserDTO updatePassword(UUID id,UserPasswordUpdateDTO userPasswordUpdateDTO) {
        log.debug("Attempting to update user password with id {} ", id);
        User userToUpdate = userRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("User not found with id: " + id);
                    return new UserNotFound("User not found with id:" + id);}
        );

        if (!passwordEncoder.matches(userPasswordUpdateDTO.getPasswordOld(), userToUpdate.getPassword())){
            log.warn("Password does not match old password");
            throw new IncorrectPassword("Old password doesn't match");
        }

        userToUpdate.setPassword(passwordEncoder.encode(userPasswordUpdateDTO.getPasswordNew()));
        userToUpdate = userRepository.save(userToUpdate);
        log.info("Updated password for user with id: " + userToUpdate.getId());
        UserDTO userDTO = userMapper.toUserDTO(userToUpdate);
        userDTO.setToken(jwtUtil.generateToken(userToUpdate.getId(), userToUpdate.getName()));
        return userDTO;
    }

    @Transactional
    @Override
    public UserDTO updateProfilePicture(UUID id, MultipartFile profilePicture) {
        log.debug("Attempting to update profile picture for user id {}", id);
        User userToUpdate = userRepository.findById(id).orElseThrow(
                () -> {
                    log.warn("User not found with id: {}", id);
                    return new UserNotFound("User not found with id:" + id);
                }
        );

        if (profilePicture == null || profilePicture.isEmpty()) {
            throw new IllegalArgumentException("Profile picture is required");
        }

        ProcessedProfilePicture processedProfilePicture = processProfilePicture(profilePicture);
        userToUpdate.setProfilePictureData(processedProfilePicture.data());
        userToUpdate.setProfilePictureContentType(processedProfilePicture.contentType());
        userToUpdate.setProfilePictureUrl("/api/user/" + id + "/profile-picture");

        userToUpdate = userRepository.save(userToUpdate);

        log.info("Updated profile picture for user id {}", id);
        return userMapper.toUserDTO(userToUpdate);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfilePictureDTO getProfilePicture(UUID id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFound("User not found with id:" + id)
        );

        if (user.getProfilePictureData() == null || user.getProfilePictureData().length == 0) {
            return null;
        }

        String contentType = user.getProfilePictureContentType() != null
                ? user.getProfilePictureContentType()
                : "image/jpeg";

        return new UserProfilePictureDTO(user.getProfilePictureData(), contentType);
    }

    private ProcessedProfilePicture processProfilePicture(MultipartFile profilePicture) {
        try {
            BufferedImage sourceImage = ImageIO.read(profilePicture.getInputStream());
            if (sourceImage == null) {
                throw new IllegalArgumentException("Uploaded file is not a supported image");
            }

            BufferedImage resizedImage = resizeImage(sourceImage, MAX_PROFILE_PICTURE_DIMENSION);
            boolean hasAlpha = resizedImage.getColorModel().hasAlpha();

            if (hasAlpha) {
                return new ProcessedProfilePicture(writePng(resizedImage), "image/png");
            }
            return new ProcessedProfilePicture(writeJpeg(resizedImage), "image/jpeg");
        } catch (IOException e) {
            throw new RuntimeException("Could not process profile picture", e);
        }
    }

    private BufferedImage resizeImage(BufferedImage sourceImage, int maxDimension) {
        int originalWidth = sourceImage.getWidth();
        int originalHeight = sourceImage.getHeight();

        double scale = Math.min(
                1.0,
                Math.min(
                        (double) maxDimension / originalWidth,
                        (double) maxDimension / originalHeight
                )
        );

        int targetWidth = Math.max(1, (int) Math.round(originalWidth * scale));
        int targetHeight = Math.max(1, (int) Math.round(originalHeight * scale));
        int targetType = sourceImage.getColorModel().hasAlpha()
                ? BufferedImage.TYPE_INT_ARGB
                : BufferedImage.TYPE_INT_RGB;

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, targetType);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.setComposite(AlphaComposite.Src);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
        graphics.dispose();

        return resizedImage;
    }

    private byte[] writePng(BufferedImage image) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(image, "png", output);
        return output.toByteArray();
    }

    private byte[] writeJpeg(BufferedImage image) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionQuality(JPEG_COMPRESSION_QUALITY);

        try (ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(output)) {
            writer.setOutput(imageOutputStream);
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } finally {
            writer.dispose();
        }

        return output.toByteArray();
    }

    private record ProcessedProfilePicture(byte[] data, String contentType) {
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserById(UUID id) {
        log.debug("Fetching user with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () ->{
                    log.warn("User not found with id: {}", id);
                    return new UserNotFound("User not found with id: " + id);
                    }
                );
        log.debug("Fetched user with id: " + user.getId());
        return userMapper.toUserDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserByEmail(String email) {
        log.debug("Fetching user with email {} ", email);
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> {
                    log.warn("User not found with email: " + email);
                    return new  UserNotFound("User not found with email: " + email);
                }
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
    public UserDTO getUserByName(String name) {
        log.debug("Fetching user with name {} ", name);
        User users = userRepository.findUserByName(name).orElseThrow(
                () -> new UserNotFound("User not found with name:" + name)
        );
        log.debug("Fetched user with name {} ", name);
        return userMapper.toUserDTO(users);
    }

    @Override
    public List<UserDTO> getAllUsersByName(String name) {
        log.debug("Searching users by name: {}", name);
        return userMapper.toUserDTOList(userRepository.findAllByNameContainingIgnoreCase(name));
    }

    @Transactional(readOnly = true)
    @Override
    public boolean isUsernameAvailable(String name) {
        String normalizedName = normalize(name);
        if (normalizedName == null || normalizedName.isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }
        return userRepository.findUserByName(normalizedName).isEmpty();
    }

    @Transactional(readOnly = true)
    @Override
    public UserWithWorkoutsDTO getUserWithWorkoutsById(UUID id) {
        log.debug("Fetching user with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () -> {
                    log.warn("User not found with id: {}", id);
                    return new UserNotFound("User not found with id: " + id);
                }
        );
        log.debug("Fetched user-workoutList with id {} ", id);
        return userMapper.toUserWithWorkoutsDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserWithFriendsDTO getUserWithFriendsById(UUID id) {
        log.debug("Fetching user-friendList with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () -> {
                    log.warn("User not found with id: {}", id);
                    return new UserNotFound("User not found with id: " + id);
                }
        );
        log.debug("Fetched user-friendList with id {} ", id);
        return userMapper.toUserWithFriendsDTO(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserWithPostsDTO getUserWithPostsById(UUID id) {
        log.debug("Fetching user-posts with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () -> {
                    log.warn("User not found with id: {}", id);
                    return new UserNotFound("User not found with id: " + id);}
        );
        log.debug("Fetched user-posts with id {} ", id);
        return userMapper.toUserWithPostsDTO(user);
    }

    @Transactional
    @Override
    public void deleteUserPost(PostDTO postDTO) {
        log.debug("Deleting user post with id {} ", postDTO.getId());
        User user = userRepository.findUserById(postDTO.getUserId()).orElseThrow(
                () -> {
                    log.warn("User not found with id: {}", postDTO.getUserId());
                    return new UserNotFound("User not found with id: " + postDTO.getUserId());}
        );
        Post post = postRepository.findById(postDTO.getId()).orElseThrow(
                () -> {
                    log.warn("Post not found with id: {}", postDTO.getId());
                    return new PostNotFoundException("Post not found with id: " + postDTO.getId());
                }
        );
        log.info("Deleted user post with id {} ", postDTO.getId());
        user.removePost(post);
    }

    @Transactional
    @Override
    public void deleteUserFriends(FriendDTO friendDTO) {
        log.debug("Deleting user friend with id {} ", friendDTO.getId());
        User user = userRepository.findUserById(friendDTO.getUserId()).orElseThrow(
                () -> {
                    log.warn("User not found with id: {}", friendDTO.getUserId());
                    return new UserNotFound("User not found with id: " + friendDTO.getUserId());}
        );
        Friend friend = friendRepository.findById(friendDTO.getId()).orElseThrow(
                () -> {
                    log.warn("Friend not found with id: {}", friendDTO.getId());
                    return new FriendNotFoundException("Friend not found with id: " + friendDTO.getId());
                }
        );
        log.info("Deleted user friend with id {} ", friend.getId());

        friendRepository.deleteById(friend.getId());
    }

    @Transactional
    @Override
    public void deleteUserWorkout(WorkoutDTO workoutDTO) {
        log.debug("Deleting user workout with id {} ", workoutDTO.getId());
        User user = userRepository.findUserById(workoutDTO.getUserId()).orElseThrow(
                ()->{
                    log.warn("User not found with id: {}", workoutDTO.getUserId());
                    return new UserNotFound("User not found with id: " + workoutDTO.getUserId());
                }
        );
        Workout workout = workoutRepository.findById(workoutDTO.getId()).orElseThrow(
                ()->{
                    log.warn("Workout not found with id: {}", workoutDTO.getId());
                    return new WorkoutNotFound("Workout not found with id: " + workoutDTO.getId());
                }
        );
        log.info("Deleted user workout with id {} ", workoutDTO.getId());
        user.removeWorkout(workout);
    }

    @Transactional
    @Override
    public void deleteUser(UUID id) {
        log.debug("Deleting user with id {} ", id);
        User user = userRepository.findUserById(id).orElseThrow(
                () -> {
                    log.warn("User not found with id: {}", id);
                    return new UserNotFound("User not found with id: " + id);}
        );

        commentRepository.nullifyUserComments(id);

        log.info("Deleted user with id: {} ",id);
        userRepository.delete(user);
    }
}
