package project.mainframe.api.chat.dto.chat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.chat.entities.Chat;

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
        this.projectId = chat.getProject().getId();
        this.memberIds = chat.getMembers().stream().map(member -> member.getId()).collect(Collectors.toList());
        this.name = chat.getName();
        this.created = chat.getCreated();
    }
}
