package hu.hm.fitjourneyapi.controller.user;

import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.dto.user.UserUpdateDTO;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/search")
    public ResponseEntity<?> getUser(
            @RequestParam(required = false) UUID id,
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

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody @Valid UserUpdateDTO dto) {
        try{
            UserDTO result = userService.updateUser(id,dto);
            return ResponseEntity.ok(result);
        }
        catch (UserNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserCreateDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(UUID id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok("Deleted");
        }catch (UserNotFound e) {
            return ResponseEntity.notFound().build();
        }
    }
}
