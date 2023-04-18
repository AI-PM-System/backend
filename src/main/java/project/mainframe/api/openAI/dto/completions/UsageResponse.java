package project.mainframe.api.openAI.dto.completions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.openAI.entities.Usage;

/**
 * The Completions response.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UsageResponse {
    
    /**
     * prompt tokens
     */
    private int prompt_tokens;

    /**
     * completion tokens
     */
    private int completion_tokens;

    /**
     * total tokens
     */
    private int total_tokens;

    /**
     * No-args constructor
     */
    public UsageResponse() {}

    /**
     * To Usage.
     */
    public Usage toUsage() {
        return new Usage(0, prompt_tokens, completion_tokens, total_tokens);
    }
}
