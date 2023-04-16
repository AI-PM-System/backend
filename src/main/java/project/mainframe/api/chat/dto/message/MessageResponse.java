package project.mainframe.api.chat.dto.message;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.chat.entities.Message;
import project.mainframe.api.project.dto.member.MemberResponse;

/**
 * Message response.
 */
@AllArgsConstructor
@Getter
@Setter
public class MessageResponse {
    
    /**
     * The id of the message
     */
    private Long id;
    
    /**
     * The message's chat id
     */
    private Long chatId;
    
    /**
     * The message's member
     */
    private MemberResponse member;
    
    /**
     * The message's content
     */
    private String content;
    
    /**
     * The message's timestamp
     */
    private LocalDateTime sent;
    
    /**
     * No-args constructor
     */
    public MessageResponse() {}

    /**
     * Map a message to a message response.
     * 
     * @param message The message to map.
     */
    public MessageResponse(Message message) {
        this.id = message.getId();
        this.chatId = message.getChat().getId();
        this.member = new MemberResponse(message.getMember());
        this.content = message.getContent();
        this.sent = message.getSent();
    }
}
