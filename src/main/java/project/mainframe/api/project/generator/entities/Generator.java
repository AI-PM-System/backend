package project.mainframe.api.project.generator.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A project generator is a generation of a new project.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project_generator")
public class Generator {
    
    /**
     * The id of the project generator
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Has many project generator messages
     */
    @OneToMany
    private List<GeneratorMessage> messages;

    /**
     * No-args constructor.
     */
    public Generator() {}
}
