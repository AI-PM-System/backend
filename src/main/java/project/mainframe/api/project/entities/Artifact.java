package project.mainframe.api.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

/**
 * An artifact is a any type of element that is part of a project.
 * 
 * Examples of artifacts are:
 * - A document
 * - A video
 * - A picture
 * - A file
 * - A link
 * - A comment
 * - A task
 * - A diagram
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Artifact {
    
    /**
     * The id of the artifact
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * An artifact belongs to one project
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
