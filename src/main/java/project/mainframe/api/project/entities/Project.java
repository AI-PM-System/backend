package project.mainframe.api.project.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.chat.entities.Chat;


/**
 * A project functions as a container for all the data 
 * related to a specific project.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Project {
    
    /**
     * The id of the project
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The name of the project
     */
    @Column(nullable = false)
    private String name;

    /**
     * The description of the project
     */
    @Column(nullable = false)
    private String description;

    /**
     * Has many project events and set eager loading
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<Event> events;

    /**
     * Has many members
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<Member> members;

    /**
     * Has many roles
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<Role> roles;

    /**
     * Has many chats
     */
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<Chat> chats;

    /**
     * No-args constructor
     */
    public Project() {}
}
