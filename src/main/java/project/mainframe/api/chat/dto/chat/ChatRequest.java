package project.mainframe.api.chat.dto.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Chat request.
 */
@AllArgsConstructor
@Getter
@Setter
public class ChatRequest {
    
    /**
     * The chat's project id
     */
    private Long projectId;

    /**
     * The chat's member ids
     */
    private List<Long> memberIds;
    
    /**
     * The chat's name
     */
    private String name;

    /**
     * The chat's type
     */
    private String type;
    
    /**
     * No-args constructor
     */
    public ChatRequest() {}
}
