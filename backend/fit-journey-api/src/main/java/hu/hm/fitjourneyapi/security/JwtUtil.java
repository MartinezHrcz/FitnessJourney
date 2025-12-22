package hu.hm.fitjourneyapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {
    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 4;

    private Key getSigningKey() {
        return  new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
    }

    public String generateToken(UUID userID, String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(userID.toString())
                .claim("username", username)
                .claim("roles",roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public List<String> extractRoles(String token){
        log.info(extractClaim(token, claims-> claims.get("roles", List.class)).toString());
        return extractClaim(token, claims-> claims.get("roles", List.class));
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("username", String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String id) {
        final String tokenID = extractUsername(token);
        return (tokenID.equals(id) && ! isTokenExpired(token));
    }

}
