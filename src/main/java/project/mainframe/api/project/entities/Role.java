package project.mainframe.api.project.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A role is a position in a project.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Role {
    
    /**
     * The id of the role
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The name of the role
     */    
    @Column(nullable = false)
    private String name;
    
    /**
     * The description of the role
     */
    @Column(nullable = false)
    private String description;
    
    /**
     * A role can have multiple members
     */
    @ManyToMany
    private List<Member> members;

    /**
     * The project this role belongs to
     */
    @ManyToOne
    private Project project;
}
