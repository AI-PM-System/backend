package project.mainframe.api.project.generator.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A generator actor defines the role of the chatbot in the project generation.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
public class GeneratorActor {
    
    /**
     * The id of the generator actor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The name of the generator actor.
     */
    private String name;

    /**
     * No-args constructor.
     */
    public GeneratorActor() {}

    /**
     * Constructor with name.
     * @param name The name of the generator actor.
     */
    public GeneratorActor(String name) {
        this.name = name;
    }
}
