package hu.hm.fitjourneyapi.controller.user;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping
    public ResponseEntity<?> getUser(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ){
        try {
            if (id != null) return ResponseEntity.ok(userService.getUserById(id));
            else if (name != null) return ResponseEntity.ok(userService.getUserByName(name));
            else if (email != null) return ResponseEntity.ok(userService.getUserByEmail(email));
            else return ResponseEntity.badRequest().build();
        }
        catch (UserNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO dto) {
        try{
            UserDTO result = userService.updateUser(dto);
            return ResponseEntity.ok(result);
        }
        catch (UserNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<UserDTO> createUser(UserCreateDTO dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ResponseEntity<String> deleteUser(int id){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
