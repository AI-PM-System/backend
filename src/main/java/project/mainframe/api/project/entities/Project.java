package project.mainframe.api.project.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/*
 * A project functions as a container for all the data 
 * related to a specific project.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Project {
    
    /*
     * The id of the project
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /*
     * The name of the project
     */
    @Column(nullable = false)
    private String name;

    /*
     * The description of the project
     */
    @Column(nullable = false)
    private String description;

    /*
     * Has many project events
     */
    @OneToMany(mappedBy = "project")
    private List<Event> events;

    /*
     * Has many members
     */
    @OneToMany
    private List<Member> members;

    /*
     * Has many roles
     */
    @OneToMany
    private List<Role> roles;

    /*
     * Has many artifacts
     */
    @OneToMany(mappedBy = "project")
    private List<Artifact> artifacts;
}
