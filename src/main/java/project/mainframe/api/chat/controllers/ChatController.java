package project.mainframe.api.chat.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.chat.dto.chat.ChatRequest;
import project.mainframe.api.chat.dto.chat.ChatResponse;
import project.mainframe.api.chat.services.ChatService;
import project.mainframe.api.project.annotations.Authorization;
import project.mainframe.api.project.entities.User;

/**
 * Chat controller.
 */
@RestController
@RequestMapping("/api/v1/user/chats")
public class ChatController {

    /**
     * The chat service
     */
    private final ChatService chatService;

    /**
     * Constructor.
     * @param chatService The chat service.
     */
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Find all chats by project id.
     * 
     * @param projectId The project id.
     * @param user The user.
     * @return List of chat responses.
     */
    @GetMapping("/project/{projectId}")
    public List<ChatResponse> findAllByProjectId(@PathVariable Long projectId, @Authorization User user) {
        return chatService.findAllByProjectId(projectId, user);
    }

    /**
     * Find by id
     * 
     * @param id The id.
     * @param user The user.
     * @return The chat response.
     */
    @GetMapping("/{id}")
    public ChatResponse findById(@PathVariable Long id, @Authorization User user) {
        return chatService.findById(id, user);
    }

    /**
     * Find by type and project id.
     * 
     * @param type The type.
     * @param projectId The project id.
     * @param user The user.
     * @return The chat response.
     */
    @GetMapping("/type/{type}/project/{projectId}")
    public ChatResponse findByTypeAndProjectId(@PathVariable String type, @PathVariable Long projectId, @Authorization User user) {
        return chatService.findByTypeAndProjectId(projectId, type, user);
    }

    /**
     * Create a new chat.
     * 
     * @param chatRequest The chat request.
     * @param user The user.
     * @return The chat response.
     */
    @PostMapping
    public ChatResponse create(@RequestBody ChatRequest chatRequest, @Authorization User user) {
        return chatService.create(chatRequest, user);
    }

    /**
     * Update a chat.
     * 
     * @param id The id.
     * @param chatRequest The chat request.
     * @param user The user.
     * @return The chat response.
     */
    @PutMapping("/{id}")
    public ChatResponse update(@PathVariable Long id, @RequestBody ChatRequest chatRequest, @Authorization User user) {
        return chatService.update(id, chatRequest, user);
    }
}