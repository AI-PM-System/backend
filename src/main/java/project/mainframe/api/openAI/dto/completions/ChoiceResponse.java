package project.mainframe.api.openAI.dto.completions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.openAI.entities.Choice;

/**
 * The Completions choice response.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChoiceResponse {
    
    /**
     * The text.
     */
    private String text;

    /**
     * The index.
     */
    private int index;

    /**
     * The logprobs.
     */
    private Object logprobs;

    /**
     * The finish reason.
     */
    private String finish_reason;

    /**
     * No-args constructor
     */
    public ChoiceResponse() {}

    /**
     * To Choice.
     */
    public Choice toChoice() {
        return new Choice(0, text, index, finish_reason);
    }
}
