package project.mainframe.api.project.generator.dto.actor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Generator actor response.
 */
@AllArgsConstructor
@Getter
@Setter
public class GeneratorActorResponse {
    
    /**
     * The generator actor's id.
     */
    private Long id;

    /**
     * The generator actor's name
     */
    private String name;

    /**
     * No-args constructor.
     */
    public GeneratorActorResponse() {}
}
