package hu.hm.fitjourneyapi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Component
public class JwtUtil {
    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 4;

    private final String TOKEN_PREFIX = "Bearer ";

    private final String HEADER_STRING = "Authorization";

    private Key getSigningKey() {
        return  new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
    }
}
