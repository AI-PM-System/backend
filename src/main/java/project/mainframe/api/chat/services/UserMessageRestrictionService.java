package project.mainframe.api.chat.services;

import org.springframework.stereotype.Service;

import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.project.services.UserProjectRestrictionService;

@Service
public class UserMessageRestrictionService {
    
    /**
     * The chat repository.
     */
    private ChatRepository chatRepository;

    /**
     * The project user restriction service
     */
    private UserProjectRestrictionService userProjectRestrictionService;

    /**
     * Constructor.
     * 
     * @param chatRepository The repository to use for CRUD operations.
     * @param userProjectRestrictionService The user project restriction service.
     */
    public UserMessageRestrictionService(
        ChatRepository chatRepository,
        UserProjectRestrictionService userProjectRestrictionService
    ) {
        this.chatRepository = chatRepository;
        this.userProjectRestrictionService = userProjectRestrictionService;
    }

    /**
     * Is member
     * 
     * @param chatId The chat id.
     * @param username The username.
     * @return true if member, false otherwise.
     */
    public boolean isMember(Long chatId, String username) {
        // find chat
        Chat chat = chatRepository.findById(chatId).orElse(null);

        // check if chat exists
        if (chat == null) {
            return false;
        }

        // check if user is a part of the chat's project
        return userProjectRestrictionService.isMember(chat.getProject().getId(), username);
    }

    /**
     * Is not member
     * 
     * @param chatId The chat id.
     * @param username The username.
     * @return true if not member, false otherwise.
     */
    public boolean isNotMember(Long chatId, String username) {
        return !isMember(chatId, username);
    }

}
