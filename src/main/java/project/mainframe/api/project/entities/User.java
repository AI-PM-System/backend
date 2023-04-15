package project.mainframe.api.project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import project.mainframe.api.security.entities.Authenticatable;

/**
 * A user is a person who is part of the system.
 * 
 * A user can be part of many projects through the member entity.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User extends Authenticatable {
    /*
     * Note:
     * The username and password is inherited 
     * from the Authenticatable class.
     * 
     * The username function as the primary key.
     */

    /**
     * The user's first name
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * The user's last name
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * The user's email address
     */
    @Column(nullable = false)
    private String email;

    /**
     * The user's phone number
     */
    @Column(nullable = false)
    private String phoneNumber;

    /**
     * No-args constructor
     */
    public User() {}
}
