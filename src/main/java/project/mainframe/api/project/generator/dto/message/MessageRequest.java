package project.mainframe.api.project.generator.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Message request.
 */
@AllArgsConstructor
@Getter
@Setter
public class MessageRequest {

    /**
     * The message's generator id
     */
    private Long generatorId;
    
    /**
     * The first message in the generation process.
     */
    private String content;
    
    /**
     * No-args constructor
     */
    public MessageRequest() {}
}
