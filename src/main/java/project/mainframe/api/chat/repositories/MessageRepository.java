package project.mainframe.api.chat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.chat.entities.Message;

/**
 * Message repository.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Find a list of messages by chat id.
     * 
     * @param chatId The chat id of the messages.
     * @return List of messages
     */
    List<Message> findAllByChatId(Long chatId);
}
