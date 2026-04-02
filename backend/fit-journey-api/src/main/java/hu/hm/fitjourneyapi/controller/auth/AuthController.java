package hu.hm.fitjourneyapi.controller.auth;

import hu.hm.fitjourneyapi.dto.user.AuthRequest;
import hu.hm.fitjourneyapi.dto.user.AuthResponse;
import hu.hm.fitjourneyapi.dto.user.RefreshTokenRequest;
import hu.hm.fitjourneyapi.dto.user.UsernameAvailabilityResponse;
import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import hu.hm.fitjourneyapi.services.interfaces.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/username-available")
    public ResponseEntity<UsernameAvailabilityResponse> usernameAvailable(@RequestParam String username) {
        if (username == null || username.trim().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        boolean available = userService.isUsernameAvailable(username);
        return ResponseEntity.ok(new UsernameAvailabilityResponse(username.trim(), available));
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserCreateDTO userCreateDTO) {
        return authService.register(userCreateDTO);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refresh(refreshTokenRequest);
    }
}
