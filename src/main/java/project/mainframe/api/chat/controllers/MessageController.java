package project.mainframe.api.chat.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.chat.dto.message.MessageRequest;
import project.mainframe.api.chat.dto.message.MessageResponse;
import project.mainframe.api.chat.services.MessageService;
import project.mainframe.api.project.annotations.Authorization;
import project.mainframe.api.project.entities.User;

/**
 * Message controller.
 */
@RestController
@RequestMapping("/api/v1/user/messages")
public class MessageController {

    /**
     * The message service
     */
    private final MessageService messageService;

    /**
     * Constructor.
     * @param messageService The message service.
     */
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Find all messages by chat id.
     * 
     * @param chatId The chat id.
     * @params user The user.
     * @return List of message responses.
     */
    @GetMapping("/chat/{chatId}")
    public List<MessageResponse> findAllByChatId(@PathVariable Long chatId, @Authorization User user) {
        return messageService.findAllByChatId(chatId, user);
    }

    /**
     * Find by id
     * 
     * @param id The id.
     * @param user The user.
     * @return The message response.
     */
    @GetMapping("/{id}")
    public MessageResponse findById(@PathVariable Long id, @Authorization User user) {
        return messageService.findById(id, user);
    }

    /**
     * Create a new message.
     * 
     * @param messageRequest The message request.
     * @param user The user.
     * @return The message response.
     */
    @PostMapping
    public MessageResponse create(@RequestBody MessageRequest messageRequest, @Authorization User user) {
        return messageService.create(messageRequest, user);
    }

    /**
     * Update a message.
     * 
     * @param id The id.
     * @param messageRequest The message request.
     * @param user The user.
     * @return The message response.
     */
    @PutMapping("/{id}")
    public MessageResponse update(@PathVariable Long id, @RequestBody MessageRequest messageRequest, @Authorization User user) {
        return messageService.update(id, messageRequest, user);
    }

    /**
     * Delete a message.
     * 
     * @param id The id.
     * @param user The user.
     * @return The message response.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @Authorization User user) {
        messageService.delete(id, user);
    }
}