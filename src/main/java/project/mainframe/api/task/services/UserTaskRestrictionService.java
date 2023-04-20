package project.mainframe.api.task.services;

import org.springframework.stereotype.Service;

import project.mainframe.api.task.entities.BoardList;
import project.mainframe.api.task.repositories.BoardListRepository;

/**
 * User task restriction service.
 */
@Service
public class UserTaskRestrictionService {
    
    /**
     * The board list repository.
     */
    private final BoardListRepository boardListRepository;

    /**
     * The User board list restriction service.
     */
    private final UserBoardListRestrictionService userBoardListRestrictionService;

    /**
     * Constructor.
     * 
     * @param boardListRepository The board list repository.
     * @param userBoardListRestrictionService The user board list restriction service.
     */
    public UserTaskRestrictionService(
        BoardListRepository boardListRepository,
        UserBoardListRestrictionService userBoardListRestrictionService
    ) {
        this.boardListRepository = boardListRepository;
        this.userBoardListRestrictionService = userBoardListRestrictionService;
    }
    
    /**
     * Is member
     * 
     * @param username The username.
     * @param boardListId The board list id.
     * return True if the user is a member of the task.
     */
    public boolean isMember(Long boardListId, String username) {
        BoardList boardList = boardListRepository.findById(boardListId).orElse(null);
        if (boardList == null) {
            return false;
        }

        return userBoardListRestrictionService.isMember(boardList.getBoard().getId(), username);
    }

    /**
     * Is not member
     * 
     * @param username The username.
     * @param taskId The task id.
     * return True if the user is not a member of the task.
     */
    public boolean isNotMember(Long boardListId, String username) {
        return !isMember(boardListId, username);
    }
}
