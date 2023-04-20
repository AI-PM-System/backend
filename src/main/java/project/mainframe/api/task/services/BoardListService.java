package project.mainframe.api.task.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.project.entities.User;
import project.mainframe.api.task.dto.boardList.BoardListRequest;
import project.mainframe.api.task.dto.boardList.BoardListResponse;
import project.mainframe.api.task.entities.Board;
import project.mainframe.api.task.entities.BoardList;
import project.mainframe.api.task.repositories.BoardListRepository;
import project.mainframe.api.task.repositories.BoardRepository;

/**
 * Board list service.
 */
@Service
public class BoardListService {
    
    /**
     * The board list repository.
     */
    private final BoardListRepository boardListRepository;

    /**
     * The board repository.
     */
    private final BoardRepository boardRepository;

    /**
     * The user board list restriction service.
     */
    private final UserBoardListRestrictionService userBoardListRestrictionService;

    /**
     * Constructor.
     * 
     * @param boardListRepository The board list repository.
     * @param boardRepository The board repository.
     * @param userBoardListRestrictionService The user board list restriction service.
     */
    public BoardListService(
        BoardListRepository boardListRepository,
        BoardRepository boardRepository,
        UserBoardListRestrictionService userBoardListRestrictionService
    ) {
        this.boardListRepository = boardListRepository;
        this.boardRepository = boardRepository;
        this.userBoardListRestrictionService = userBoardListRestrictionService;
    }

    /**
     * Find all board lists.
     * @param boardId The board id.
     * @param actor The actor.
     * @return The list of responses.
     */
    public List<BoardListResponse> findAllByBoardId(Long boardId, User actor) {
        if (userBoardListRestrictionService.isNotMember(boardId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board's project.");
        }

        return boardListRepository
            .findAllByBoardId(boardId)
            .stream()
            .map(BoardListResponse::new)
            .toList();
    }

    /**
     * Find by id.
     * 
     * @param id The id.
     * @param actor The actor.
     * @return The response.
     */
    public BoardListResponse findById(Long id, User actor) {
        BoardList boardList = boardListRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board list not found."));
        if (userBoardListRestrictionService.isNotMember(boardList.getBoard().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board's project.");
        }

        return new BoardListResponse(boardList);
    }

    /**
     * Create a board list.
     * 
     * @param boardId The board id.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    public BoardListResponse create(BoardListRequest request, User actor) {
        if (userBoardListRestrictionService.isNotMember(request.getBoardId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board's project.");
        }

        Board board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found."));
        BoardList boardList = new BoardList();
        boardList.setName(request.getName());
        boardList.setBoard(board);
        boardListRepository.save(boardList);

        return new BoardListResponse(boardList);
    }

    /**
     * Update a board list.
     * 
     * @param id The id.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    public BoardListResponse update(Long id, BoardListRequest request, User actor) {
        BoardList boardList = boardListRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board list not found."));
        if (userBoardListRestrictionService.isNotMember(boardList.getBoard().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board's project.");
        }

        boardList.setName(request.getName());
        boardListRepository.save(boardList);

        return new BoardListResponse(boardList);
    }

    /**
     * Delete a board list.
     * 
     * @param id The id.
     * @param actor The actor.
     */
    public void delete(Long id, User actor) {
        BoardList boardList = boardListRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board list not found."));
        if (userBoardListRestrictionService.isNotMember(boardList.getBoard().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the board's project.");
        }

        boardListRepository.delete(boardList);
    }
}
