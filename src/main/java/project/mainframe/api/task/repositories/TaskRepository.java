package project.mainframe.api.task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.task.entities.Task;

/**
 * Task repository.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find all by board list id.
     * 
     * @param boardListId The board list id.
     * @return The tasks.
     */
    List<Task> findAllByBoardListId(Long boardListId);
}
