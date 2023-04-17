package project.mainframe.api.chat.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.chat.dto.chat.ChatRequest;
import project.mainframe.api.chat.dto.chat.ChatResponse;
import project.mainframe.api.chat.dto.message.MessageRequest;
import project.mainframe.api.chat.dto.message.MessageResponse;
import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.chat.services.ChatService;

/**
 * Chat controller.
 */
@RestController
@RequestMapping("/api/v1/user/chats")
public class ChatController extends BaseCrudController<ChatRequest, ChatResponse, Chat, Long> {

    /**
     * Constructor.
     * @param baseCrudService Base crud service.
     */
    public ChatController(BaseCrudService<ChatRequest, ChatResponse, Chat, Long> baseCrudService) {
        super(baseCrudService);
    }
    
    /**
     * Create a new generator chat by sending a message.
     * 
     * @param messageRequest The message request.
     * @return Chat response.
     */
    @PostMapping("/generator")
    public MessageResponse createGeneratorChat(@RequestBody MessageRequest messageRequest) {
        ChatService chatService = (ChatService) this.baseCrudService;
        return chatService.create(ChatType.GENERATOR, "Project Generator", messageRequest);
    }
}