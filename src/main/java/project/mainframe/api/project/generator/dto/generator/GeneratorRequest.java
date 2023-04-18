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
     * No-args constructor
     */
    public GeneratorRequest() {}
}
