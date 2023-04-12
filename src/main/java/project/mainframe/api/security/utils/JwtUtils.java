package project.mainframe.api.security.utils;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    @Value("${jwt.issuer}")
    private String ISSUER;
    
    // Generate a JWT token
    public String generateJwtToken(String username, List<String> authorities) {
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setIssuer(ISSUER)
                .compact();
    }

    // Parse and validate a JWT token
    public Claims parseJwtToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // Validate if a JWT token is expired
    public boolean isJwtTokenExpired(String token) {
        return parseJwtToken(token).getExpiration().before(new Date());
    }

    // Validate if a JWT token is valid
    public boolean isJwtTokenValid(String token) {
        return !isJwtTokenExpired(token);
    }
}
