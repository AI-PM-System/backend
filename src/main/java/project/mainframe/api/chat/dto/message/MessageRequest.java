package project.mainframe.api.chat.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Message request.
 */
@AllArgsConstructor
@Getter
@Setter
public class MessageRequest {
    
    /**
     * The message's chat id
     */
    private Long chatId;
    
    /**
     * The message's member id
     */
    private Long memberId;

    /**
     * The message's user's username
     */
    private String username;
    
    /**
     * The message's content
     */
    private String content;
    
    /**
     * No-args constructor
     */
    public MessageRequest() {}
}
