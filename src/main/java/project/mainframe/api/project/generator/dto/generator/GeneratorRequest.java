package project.mainframe.api.project.generator.dto.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Generator request.
 */
@AllArgsConstructor
@Getter
@Setter
public class GeneratorRequest {
    
    /**
     * The first message in the generation process.
     */
    private String content;

    /**
     * The generator's actor id.
     */
    private Long actorId;

    /**
     * The generator's tone id.
     */
    private Long toneId;
    
    /**
     * No-args constructor
     */
    public GeneratorRequest() {}
}
