package project.mainframe.api.chat.dto.chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.enums.ChatType;

/**
 * Message response.
 */
@AllArgsConstructor
@Getter
@Setter
public class ChatResponse {
    
    /**
     * The id of the chat
     */
    private Long id;
    
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
    private ChatType type;
    
    /**
     * The chat's timestamp
     */
    private LocalDateTime created;
    
    /**
     * No-args constructor
     */
    public ChatResponse() {}

    /**
     * Map a chat to a chat response.
     * 
     * @param chat The chat to map.
     */
    public ChatResponse(Chat chat) {
        this.id = chat.getId();
        this.projectId = chat.getProject() != null ? chat.getProject().getId() : null;
        this.memberIds = chat.getMembers() != null ? chat.getMembers().stream().map(m -> m.getId()).collect(Collectors.toList()) : new ArrayList<>();
        this.name = chat.getName();
        this.created = chat.getCreated();
        this.type = chat.getType();
    }
}
