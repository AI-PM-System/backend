package project.mainframe.api.security.utils;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    /*
     * The secret key that is used to sign the JWT token.
     */
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /*
     * The expiration time of the JWT token.
     */
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;

    /*
     * The issuer of the JWT token.
     */
    @Value("${jwt.issuer}")
    private String ISSUER;

    /*
     * The audience of the JWT token.
     */
    @Value("${jwt.audience}")
    private String AUDIENCE;
    
    /*
     * Generates a JWT token for a given username and list of authorities
     * 
     * @param username the username of the user
     * @param authorities the list of authorities of the user
     * @return the generated JWT token
     */
    public String generateJwtToken(String username, List<String> authorities) {
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setAudience(AUDIENCE)
                .setIssuer(ISSUER)
                .compact();
    }

    /*
     * Parses, validates and returns the claims of a JWT token
     * 
     * @param token the JWT token to parse
     * @return the claims of the token
     * 
     * @throws SignatureException if the signature of the token is invalid
     * 
     * @throws ExpiredJwtException if the token is expired
     * 
     * @throws MissingClaimException if the token is missing a claim (e.g. the audience or issuer is missing)
     * 
     * @throws InvalidClaimException if the token has an invalid claim (e.g. the audience or issuer is invalid)
     */
    public Claims parseAndValidateJwtToken(String token) throws 
        SignatureException, ExpiredJwtException, MissingClaimException, InvalidClaimException, RuntimeException {
        return Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .requireAudience(AUDIENCE)
            .requireIssuer(ISSUER)
            .parseClaimsJws(token)            
            .getBody();
    }

    /*
     * Returns the expiration date of a JWT token
     * 
     * @param token the JWT token to parse
     * @return the expiration date of the token
     */
    public LocalDateTime getJwtTokenExpirationDate(Claims claims) {
        return claims
            .getExpiration()
            .toInstant()
            .atZone(java.time.ZoneId.systemDefault())
            .toLocalDateTime();
    }

    /*
     * Returns the authorities of a JWT token
     * 
     * @param token the JWT token to parse
     * @return the authorities of the token
     */
    public List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        List<String> authoritiesList = Arrays.asList(claims.get("authorities").toString().split(","));
        return authoritiesList.stream()
                .map(a -> new SimpleGrantedAuthority(a.replace("[", "").replace("]", "")))
                .collect(Collectors.toList());
    }
}
