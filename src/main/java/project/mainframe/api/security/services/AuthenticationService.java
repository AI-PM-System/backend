package project.mainframe.api.security.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.security.dto.AuthenticationRequest;
import project.mainframe.api.security.dto.AuthenticationResponse;
import project.mainframe.api.security.entities.Authenticatable;
import project.mainframe.api.security.utils.JwtUtils;

/**
 * This service is responsible for authenticating users and generating JWT tokens
 * for them.
 */
@Service
public class AuthenticationService {
    
    /**
     * This service is responsible for authenticating users and generating JWT tokens
     * for them.
     */
    private JwtUtils jwtUtils;

    /**
     * This service is responsible for loading users from the database.
     */
    private AuthenticatableDetailsService authenticatableDetailsService;
    
    /**
     * This service is responsible for encoding passwords.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * This constructor is called by Spring when the application starts.
     * 
     * @param jwtUtils The service responsible for generating and validating JWT tokens
     * @param authenticatableDetailsService The service responsible for loading users from the database
     * @param passwordEncoder The service responsible for encoding passwords
     */
    public AuthenticationService(JwtUtils jwtUtils, AuthenticatableDetailsService authenticatableDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.authenticatableDetailsService = authenticatableDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method is called by the AuthenticationController when a user tries to
     * authenticate. It is responsible for checking the username and password and
     * generating a JWT token for the user.
     * 
     * @param authenticationRequest The request containing the username and password
     * @return The response containing the JWT token
     * 
     * @throws ResponseStatusException If the username or password is invalid
     * @throws UsernameNotFoundException If the user is not found
     */
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) 
            throws ResponseStatusException, UsernameNotFoundException {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        Authenticatable user = authenticatableDetailsService.loadUserByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        List<String> authorities = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return new AuthenticationResponse(jwtUtils.generateJwtToken(authenticationRequest.getUsername(), authorities));
    }
}
