package project.mainframe.api.security.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.security.dto.AuthenticationRequest;
import project.mainframe.api.security.dto.AuthenticationResponse;
import project.mainframe.api.security.entities.Authenticatable;
import project.mainframe.api.security.utils.JwtUtils;

@Service
public class AuthenticationService {
    
    private JwtUtils jwtUtils;
    private AuthenticatableDetailsService authenticatableDetailsService;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(JwtUtils jwtUtils, AuthenticatableDetailsService authenticatableDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.authenticatableDetailsService = authenticatableDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
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
