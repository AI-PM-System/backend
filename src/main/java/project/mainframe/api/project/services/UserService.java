package project.mainframe.api.project.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.project.dto.user.UserRequest;
import project.mainframe.api.project.dto.user.UserResponse;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.repositories.UserRepository;
import project.mainframe.api.security.dto.AuthenticationResponse;
import project.mainframe.api.security.utils.JwtUtils;

/**
 * User service.
 */
@Service
public class UserService {

    /**
     * The user repository
     */
    private UserRepository userRepository;

    /**
     * JwtUtils
     */
    private JwtUtils jwtUtils;
    
    /**
     * Constructor.
     * 
     * @param userRepository The repository to use for CRUD operations.
     * @param jwtUtils The jwt utils.
     */
    public UserService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    /**
     * Find user by id.
     * 
     * @param id The id.
     * @return The user.
     */
    public UserResponse findById(String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserResponse(user);
    }

    /**
     * Create user with request and role.
     * 
     * @param request The request.
     * @param role The role.
     * @return The user.
     */
    public AuthenticationResponse create(UserRequest request, String role) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAuthorities(Collections.singletonList(role));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user = userRepository.save(user);

        // Automatically login after registration
        List<String> roles = user.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList());
        String jwt = jwtUtils.generateJwtToken(user.getUsername(), roles);
        return new AuthenticationResponse(jwt);
    }

    /**
     * Update user.
     * 
     * @param id The id.
     * @param request The request.
     * @return The user.
     */
    public UserResponse update(UserRequest request, String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
        return new UserResponse(user);
    }
}
