package hu.hm.fitjourneyapi.services.interfaces.security;

import hu.hm.fitjourneyapi.dto.user.AuthRequest;
import hu.hm.fitjourneyapi.dto.user.AuthResponse;
import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;

public interface AuthService {
    AuthResponse register(UserCreateDTO request);
    AuthResponse login(AuthRequest request);
}
