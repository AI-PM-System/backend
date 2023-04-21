package project.mainframe.api.project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.project.annotations.Authorization;
import project.mainframe.api.project.dto.user.UserRequest;
import project.mainframe.api.project.dto.user.UserResponse;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.services.UserService;
import project.mainframe.api.security.dto.AuthenticationResponse;

/**
 * User controller.
 */
@RestController
@RequestMapping("/v1")
public class UserController {

    /**
     * The user service
     */
    private final UserService userService;

    /**
     * Constructor.
     * @param userService The user service.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Find authenticated user
     * 
     * @param user The user.
     * @return The user response.
     */
    @GetMapping("/user/me")
    public UserResponse findMe(@Authorization User user) {
        return userService.findById(user.getUsername());
    }

    /**
     * Create user with the role: user;
     * 
     * @param userRequest The user request.
     * @return The user response.
     */
    @PostMapping("/public/user")
    public AuthenticationResponse create(@RequestBody UserRequest userRequest) {

        // Disabled for security reasons.
        throw new UnsupportedOperationException("This endpoint is disabled for security reasons.");

        //return userService.create(userRequest, "ROLE_USER");
    }

    /**
     * Update user.
     * 
     * @param userRequest The user request.
     * @param user The user.
     * @return The user response.
     */
    @PutMapping("/user/me")
    public UserResponse update(@RequestBody UserRequest userRequest, @Authorization User user) {
        return userService.update(userRequest, user.getUsername());
    }
}
