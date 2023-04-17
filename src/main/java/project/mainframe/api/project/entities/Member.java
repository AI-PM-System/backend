package project.mainframe.api.project.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A member is a person who is part of a project.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Member {
    
    /**
     * The member's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * A member can have multiple roles
     */
    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Role> roles;

    /**
     * The member belongs to one project
     */
    @ManyToOne(optional = false)
    private Project project;

    /**
     * The member is an AI if no user is assigned
     */
    @ManyToOne(optional = true)
    private User user;

    /**
     * No-args constructor
     */
    public Member() {}

    /**
     * A member is automatically an AI if no user is assigned.
     * 
     * @return true if the member is an AI, false otherwise
     */
    public boolean isAI() {
        return user == null;
    }
}
