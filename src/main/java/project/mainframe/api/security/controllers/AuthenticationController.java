package project.mainframe.api.security.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.security.dto.AuthenticationRequest;
import project.mainframe.api.security.dto.AuthenticationResponse;
import project.mainframe.api.security.services.AuthenticationService;

/**
 * Authentication controller.
 * 
 * This controller handles authentication requests.
 */
@RestController
@RequestMapping("/api/v1/public/authenticate")
public class AuthenticationController {
    
    /**
     * The service that handles authentication.
     */
    private AuthenticationService authenticationService;

    /**
     * Constructor.
     * 
     * @param authenticationService The service that handles authentication
     */
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticate a user.
     * 
     * The request contains the username and password of the user.
     * The response contains a JWT token that can be used to authenticate
     * the user in subsequent requests.
     * 
     * @param authenticationRequest The request containing the username and password
     * 
     * @return The response containing the JWT token
     */
    @PostMapping
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }

}
