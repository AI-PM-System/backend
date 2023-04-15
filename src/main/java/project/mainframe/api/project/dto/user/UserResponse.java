package project.mainframe.api.project.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mainframe.api.project.entities.User;

/**
 * User response.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    
    /**
     * The username of the user.
     */
    private String username;

    /**
     * The user's first name
     */
    private String firstName;

    /**
     * The user's last name
     */
    private String lastName;

    /**
     * The user's email address
     */
    private String email;

    /**
     * The user's phone number
     */
    private String phoneNumber;    

    /**
     * Constructs a UserResponse object from a User object.
     * @param user The User object to be converted.
     */
    public UserResponse(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }
}
