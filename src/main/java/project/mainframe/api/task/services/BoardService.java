package project.mainframe.api.task.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.repositories.ProjectRepository;
import project.mainframe.api.project.services.UserProjectRestrictionService;
import project.mainframe.api.task.dto.board.BoardRequest;
import project.mainframe.api.task.dto.board.BoardResponse;
import project.mainframe.api.task.entities.Board;
import project.mainframe.api.task.repositories.BoardRepository;

/**
 * Board service.
 */
@Service
public class BoardService {
    
    /**
     * User Project Restriction service.
     */
    private UserProjectRestrictionService userProjectRestrictionService;

    /**
     * Board repository.
     */
    private BoardRepository boardRepository;

    /**
     * Project repository.
     */
    private ProjectRepository projectRepository;

    /**
     * Constructor.
     * 
     * @param userProjectRestrictionService The user project restriction service.
     * @param boardRepository The board repository.
     * @param projectRepository The project repository.
     */
    public BoardService(
        UserProjectRestrictionService userProjectRestrictionService,
        BoardRepository boardRepository,
        ProjectRepository projectRepository
    ) {
        this.userProjectRestrictionService = userProjectRestrictionService;
        this.boardRepository = boardRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Find all boards.
     * @param projectId The project id.
     * @param actor The actor.
     * @return The list of responses.
     */
    public List<BoardResponse> findAllByProjectId(Long projectId, User actor) {
        if (userProjectRestrictionService.isNotMember(projectId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        return boardRepository
            .findAllByProjectId(projectId)
            .stream()
            .map(BoardResponse::new)
            .toList();
    }
    
    /**
     * Find by id.
     * 
     * @param id The id.
     * @param actor The actor.
     * @return The response.
     */
    public BoardResponse findById(Long id, User actor) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found."));
        if (userProjectRestrictionService.isNotMember(board.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        return new BoardResponse(board);
    }

    /**
     * Create a board.
     * 
     * @param projectId The project id.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    public BoardResponse create(BoardRequest request, User actor) {
        if (userProjectRestrictionService.isNotMember(request.getProjectId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        Board board = new Board();
        board.setName(request.getName());
        board.setDescription(request.getDescription());
        board.setProject(projectRepository.findById(request.getProjectId()).get());
        return new BoardResponse(boardRepository.save(board));
    }

    /**
     * Update a board.
     * 
     * @param id The id.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    public BoardResponse update(Long id, BoardRequest request, User actor) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found."));
        if (userProjectRestrictionService.isNotMember(board.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        board.setName(request.getName());
        board.setDescription(request.getDescription());
        return new BoardResponse(boardRepository.save(board));
    }

    /**
     * Delete a board.
     * 
     * @param id The id.
     * @param actor The actor.
     */
    public void delete(Long id, User actor) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found."));
        if (userProjectRestrictionService.isNotMember(board.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        boardRepository.deleteById(id);
    }
}
