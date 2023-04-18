package project.mainframe.api.chat.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.chat.dto.chat.ChatRequest;
import project.mainframe.api.chat.dto.chat.ChatResponse;
import project.mainframe.api.chat.entities.Chat;

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
}