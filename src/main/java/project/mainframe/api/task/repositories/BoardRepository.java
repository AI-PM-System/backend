package project.mainframe.api.task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.task.entities.Board;

/**
 * Board repository.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * Find all by project id.
     * 
     * @param projectId The project id.
     * @return The boards.
     */
    List<Board> findAllByProjectId(Long projectId);
}
