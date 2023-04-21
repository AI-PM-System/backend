package project.mainframe.api.project.generator.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A generator tone defines the tone of the chatbot in the project generation.
 */
@AllArgsConstructor
@Getter
@Setter
@Entity
public class GeneratorTone {
    
    /**
     * The id of the generator actor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The tone
     */
    private String tone;

    /**
     * No-args constructor.
     */
    public GeneratorTone() {}

    /**
     * Constructor with tone.
     * @param tone The tone of the generator tone.
     */
    public GeneratorTone(String tone) {
        this.tone = tone;
    }
}
