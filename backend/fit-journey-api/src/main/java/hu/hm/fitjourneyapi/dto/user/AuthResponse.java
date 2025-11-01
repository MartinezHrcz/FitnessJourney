package hu.hm.fitjourneyapi.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    UserDTO user;
    String token;
}
