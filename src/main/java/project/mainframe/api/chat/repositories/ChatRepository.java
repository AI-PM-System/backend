package project.mainframe.api.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.chat.entities.Chat;

/**
 * Chat repository.
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}