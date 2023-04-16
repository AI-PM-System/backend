package project.mainframe.api.chat.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.chat.dto.message.MessageRequest;
import project.mainframe.api.chat.dto.message.MessageResponse;
import project.mainframe.api.chat.entities.Message;

/**
 * Message controller.
 */
@RestController
@RequestMapping("/api/v1/user/messages")
public class MessageController extends BaseCrudController<MessageRequest, MessageResponse, Message, Long> {

    /**
     * Constructor.
     * @param baseCrudService Base crud service.
     */
    public MessageController(BaseCrudService<MessageRequest, MessageResponse, Message, Long> baseCrudService) {
        super(baseCrudService);
    }
}