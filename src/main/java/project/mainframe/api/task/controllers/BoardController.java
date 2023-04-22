package project.mainframe.api.task.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.project.annotations.Authorization;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.task.dto.board.BoardRequest;
import project.mainframe.api.task.dto.board.BoardResponse;
import project.mainframe.api.task.services.BoardService;

/**
 * Board controller.
 */
@RestController
@RequestMapping("/v1/user/boards")
public class BoardController {
    
    /**
     * Board service.
     */
    private BoardService boardService;

    /**
     * Constructor.
     * @param boardService The board service.
     */
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /**
     * Find all boards.
     * @param projectId The project id.
     * @param actor The actor.
     * @return The list of responses.
     */
    @GetMapping("/project/{projectId}")
    public List<BoardResponse> findAllByProjectId(@PathVariable Long projectId, @Authorization User actor) {
        return boardService.findAllByProjectId(projectId, actor);
    }

    /**
     * Find by id.
     * @param id The id.
     * @param actor The actor.
     * @return The response.
     */
    @GetMapping("/{id}")
    public BoardResponse findById(@PathVariable Long id, @Authorization User actor) {
        return boardService.findById(id, actor);
    }

    /**
     * Create a new board.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    @PostMapping
    public BoardResponse create(@RequestBody BoardRequest request, @Authorization User actor) {
        return boardService.create(request, actor);
    }

    /**
     * Update a board.
     * @param id The id.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    @PutMapping("/{id}")
    public BoardResponse update(@PathVariable Long id, @RequestBody BoardRequest request, @Authorization User actor) {
        return boardService.update(id, request, actor);
    }

    /**
     * Delete a board.
     * @param id The id.
     * @param actor The actor.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @Authorization User actor) {
        boardService.delete(id, actor);
    }
}
