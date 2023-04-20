package project.mainframe.api.task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.task.entities.BoardList;

/**
 * Board list repository.
 */
@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {

    /**
     * Find all by board id.
     * 
     * @param boardId The board id.
     * @return The board lists.
     */
    List<BoardList> findAllByBoardId(Long boardId);
}
