package project.mainframe.api.project.generator.dto.tone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Generator tone response.
 */
@AllArgsConstructor
@Getter
@Setter
public class GeneratorToneResponse {
    
    /**
     * The generator tone's id.
     */
    private Long id;

    /**
     * The generator tone's tone
     */
    private String tone;

    /**
     * No-args constructor.
     */
    public GeneratorToneResponse() {}
}
