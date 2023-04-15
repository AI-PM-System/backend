package project.mainframe.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The request to authenticate a user.
 * 
 * The request contains the username and password of the user.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequest {
    
    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * No-args constructor
     */
    public AuthenticationRequest() {}
}
