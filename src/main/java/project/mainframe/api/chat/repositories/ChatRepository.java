package project.mainframe.api.chat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.enums.ChatType;

/**
 * Chat repository.
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    /**
     * Find a chat by type and project id.
     * 
     * @param type The type of the chat.
     * @param projectId The project id of the chat.
     * @return Chat
     */
    Chat findByTypeAndProjectId(ChatType type, Long projectId);

    /**
     * Find a chat by project id.
     * 
     * @param projectId The project id of the chat.
     * @return Chat
     */
    List<Chat> findAllByProjectId(Long projectId);
}