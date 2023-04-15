package project.mainframe.api.project.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mainframe.api.security.dto.AuthenticationRequest;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest extends AuthenticationRequest {
    /*
     * Note:
     * The request also expects a username and password,
     * which are inherited from AuthenticationRequest
     */
    
    /*
     * The user's first name
     */
    private String firstName;

    /*
     * The user's last name
     */
    private String lastName;

    /*
     * The user's email address
     */
    private String email;

    /*
     * The user's phone number
     */
    private String phoneNumber;
}
