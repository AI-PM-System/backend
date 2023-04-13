package project.mainframe.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * The response to an authentication request.
 * 
 * The response contains a JWT token that can be used to authenticate
 * the user in subsequent requests.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    
    private String jwt;
}
