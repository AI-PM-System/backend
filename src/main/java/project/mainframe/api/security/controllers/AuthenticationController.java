package project.mainframe.api.security.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.security.dto.AuthenticationRequest;
import project.mainframe.api.security.dto.AuthenticationResponse;
import project.mainframe.api.security.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return authenticationService.authenticate(authenticationRequest);
    }

}
