package project.mainframe.api.project.generator.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.dto.user.UserResponse;
import project.mainframe.api.project.generator.dto.gpt.GPTResponse;
import project.mainframe.api.project.generator.entities.GeneratorMessage;

/**
 * Message response.
 */
@AllArgsConstructor
@Getter
@Setter
public class MessageResponse {
    
    /**
     * The message's id.
     */
    private Long id;

    /**
     * The message's generator id
     */
    private Long generatorId;

    /**
     * The message.
     */
    private String content;

    /**
     * The project response.
     */
    private GPTResponse gptResponse;

    /**
     * The user response.
     */
    private UserResponse user;

    /**
     * No-args constructor
     */
    public MessageResponse() {}

    /**
     * Constructor.
     * 
     * @param GeneratorMessage message
     */
    public MessageResponse(GeneratorMessage message, GPTResponse gptResponse) {
        this.id = message.getId();
        this.generatorId = message.getProjectGenerator().getId();
        this.content = message.getContent();
        this.gptResponse = gptResponse;
        this.user = message.getUser() != null ? new UserResponse(message.getUser()) : null;
    }
}
