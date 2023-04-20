package project.mainframe.api.chat.dto.message;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.chat.entities.Message;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.project.dto.user.UserResponse;

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
     * The member's user
     */
    private UserResponse user;
    
    /**
     * The message's content
     */
    private String content;
    
    /**
     * The message's timestamp
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
        this.member = message.getMember() != null ? new MemberResponse(message.getMember()) : null;
        this.user = message.getMember() != null && message.getMember().getUser() != null ? new UserResponse(message.getMember().getUser()) : null;
        this.content = message.getContent();
        this.sent = message.getSent();
    }
}
