package project.mainframe.api.security.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import project.mainframe.api.security.utils.JwtUtils;

/**
 * The Authenticatable Identity Service.
 */
@Service
public abstract class AuthenticatableIdentityService<E, ID> {
    
    /**
     * The Jwt utils.
     */
    private final JwtUtils jwtUtils;

    /**
     * Authenticatable repository.
     */
    private JpaRepository<E, ID> authenticatableRepository;

    /**
     * Constructor.
     * @param jwtUtils The jwt utils.
     * @param authenticatableRepository The authenticatable repository.
     */
    public AuthenticatableIdentityService(JwtUtils jwtUtils, JpaRepository<E, ID> authenticatableRepository) {
        this.jwtUtils = jwtUtils;
        this.authenticatableRepository = authenticatableRepository;
    }

    /**
     * Get the authenticatable identity from authorization header.
     * @param authorizationHeader the header.
     * @return The authenticatable.
     */
    public E getAuthenticatableIdentity(String authorizationHeader) {
        Claims claims = jwtUtils.parseAndValidateJwtTokenByHeader(authorizationHeader);
        ID username = (ID) claims.getSubject();
        return authenticatableRepository.findById(username).orElseThrow(
            () -> new RuntimeException("User not found")
        );
    }
}
