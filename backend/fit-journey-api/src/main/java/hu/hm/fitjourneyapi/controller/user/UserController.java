package hu.hm.fitjourneyapi.controller.user;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResponseEntity<UserDTO> getUserByName(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResponseEntity<UserDTO> getUserById(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResponseEntity<UserDTO> getUserByEmail(String email){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResponseEntity<UserDTO> updateUser(UserUpdateDTO dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResponseEntity<UserDTO> createUser(UserCreateDTO dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResponseEntity<String> deleteUser(int id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
