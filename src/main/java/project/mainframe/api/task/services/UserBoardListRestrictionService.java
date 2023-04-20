package project.mainframe.api.task.services;

import org.springframework.stereotype.Service;

import project.mainframe.api.project.services.UserProjectRestrictionService;
import project.mainframe.api.task.entities.Board;
import project.mainframe.api.task.repositories.BoardRepository;

/**
 * User board list restriction service.
 */
@Service
public class UserBoardListRestrictionService {

    /**
     * The user project restriction service.
     */
    private final UserProjectRestrictionService userProjectRestrictionService;

    /**
     * The board repository.
     */
    private final BoardRepository boardRepository;

    /**
     * Constructor.
     * 
     * @param userProjectRestrictionService The user project restriction service.
     * @param boardRepository The board repository.
     */
    public UserBoardListRestrictionService(
        UserProjectRestrictionService userProjectRestrictionService,
        BoardRepository boardRepository
    ) {
        this.userProjectRestrictionService = userProjectRestrictionService;
        this.boardRepository = boardRepository;
    }

    /**
     * Is member
     * 
     * @param username The username.
     * @param boarId The board id.
     * return True if the user is a member of the board list.
     */
    public boolean isMember(Long boarId, String username) {
        Board board = boardRepository.findById(boarId).orElse(null);
        if (board == null) {
            return false;
        }

        return userProjectRestrictionService.isMember(board.getProject().getId(), username);
    }

    /**
     * Is not member
     * 
     * @param username The username.
     * @param boarId The board id.
     * return True if the user is not a member of the board list.
     */
    public boolean isNotMember(Long boarId, String username) {
        return !isMember(boarId, username);
    }
}
