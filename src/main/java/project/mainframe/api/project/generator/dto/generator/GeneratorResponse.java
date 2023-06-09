package project.mainframe.api.project.generator.dto.generator;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.generator.dto.message.MessageResponse;

/**
 * Generator response.
 */
@AllArgsConstructor
@Getter
@Setter
public class GeneratorResponse {
    
    /**
     * The generator's id.
     */
    private Long id;

    /**
     * The generator's messages
     */
    private List<MessageResponse> messages;

    /**
     * Is the generator completed?
     */
    private boolean completed;

    /**
     * The generator's main chat id
     */
    private Long mainChatId;

    /**
     * No-args constructor.
     */
    public GeneratorResponse() {}
}
