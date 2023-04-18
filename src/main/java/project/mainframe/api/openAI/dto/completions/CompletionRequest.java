package project.mainframe.api.openAI.dto.completions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Completions request.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompletionRequest {
    
    /**
     * The model.
     */
    private String model;

    /**
     * The prompt.
     */
    private String prompt;

    /**
     * The temperature.
     */
    private double temperature;

    /**
     * The max tokens.
     */
    private int maxTokens;

    /**
     * The top p.
     */
    private double topP;

    /**
     * The frequency penalty.
     */
    private double frequencyPenalty;

    /**
     * The presence penalty.
     */
    private double presencePenalty;

    /**
     * No-args constructor
     */
    public CompletionRequest() {}
}
