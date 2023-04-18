package project.mainframe.api.openAI.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.openAI.entities.Completion;

@Repository
public interface CompletionRepository extends JpaRepository<Completion, Long> {

    /**
     * Exists by id
     * 
     * @param id the id
     * @return boolean
     */
    boolean existsById(String id);

    /** 
     * Get number of completions between to LocalDateTime objects
     * 
     * @return int
     */
    int countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}