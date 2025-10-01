package hu.hm.fitjourneyapi.services.interfaces;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserPasswordUpdateDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserCreateDTO userCreateDTO);

    UserDTO updateUser(UserUpdateDTO userUpdateDTO);

    UserDTO updatePassword(UserPasswordUpdateDTO userPasswordUpdateDTO);

    UserDTO getUserById(long id);

    UserDTO getUserByEmail(String email);

    List<UserDTO> getAllUsers();

    List<UserDTO> getUsersByName(String name);

    void deleteUser(long id);

}
