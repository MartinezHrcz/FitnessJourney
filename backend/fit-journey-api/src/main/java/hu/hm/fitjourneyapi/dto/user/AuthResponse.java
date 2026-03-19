package hu.hm.fitjourneyapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    UserDTO user;
    String token;
    String refreshToken;
}
