package hu.hm.fitjourneyapi.services.interfaces.security;

import hu.hm.fitjourneyapi.dto.user.AuthRequest;
import hu.hm.fitjourneyapi.dto.user.AuthResponse;
import hu.hm.fitjourneyapi.dto.user.UserCreateDTO;

public interface AuthService {
    public AuthResponse register(UserCreateDTO request);
    public AuthResponse login(AuthRequest request);
}
