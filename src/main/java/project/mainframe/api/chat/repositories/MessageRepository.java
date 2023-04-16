package project.mainframe.api.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.chat.entities.Message;

/**
 * Message repository.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
