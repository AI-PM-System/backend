package project.mainframe.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The response to an authentication request.
 * 
 * The response contains a JWT token that can be used to authenticate
 * the user in subsequent requests.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {
    
    /**
     * The JWT token.
     */
    private String jwt;

    /**
     * No-args constructor
     */
    public AuthenticationResponse() {}
}
