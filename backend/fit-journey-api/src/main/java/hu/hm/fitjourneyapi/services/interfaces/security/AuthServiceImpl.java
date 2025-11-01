package hu.hm.fitjourneyapi.services.interfaces.security;

import hu.hm.fitjourneyapi.dto.user.AuthRequest;
import hu.hm.fitjourneyapi.dto.user.AuthResponse;
import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;
import hu.hm.fitjourneyapi.dto.user.UserDTO;
import hu.hm.fitjourneyapi.security.JwtUtil;
import hu.hm.fitjourneyapi.services.interfaces.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse register(UserCreateDTO request) {
        UserDTO user = userService.createUser(request);
        String token = jwtUtil.generateToken(user.getId(),user.getName(), List.of(user.getRole().name()));
        return new AuthResponse(user,token);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        UserDTO user = userService.getUserByName(request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtUtil.generateToken(user.getId(),user.getName(), List.of(user.getRole().name()));
        return new AuthResponse(user,token);
    }
}
