package project.mainframe.api.project.generator.dto.gpt;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * GPT response.
 */
@AllArgsConstructor
@Getter
@Setter
public class GPTResponse {

    /**
     * The project
     */
    private Map<String, String> project;

    /**
     * The roles
     */
    private List<Map<String, String>> roles;

    /**
     * The members
     */
    private List<Map<String, Object>> members;

    /**
     * The events
     */
    private List<Map<String, Object>> events;

    /**
     * The artifacts
     */
    // private List<Map<String, Object>> artifacts;

    /**
     * The main chat
     */
    // private Map<String, Object> mainChat;

    /**
     * no-args constructor
     */
    public GPTResponse() {}
}
