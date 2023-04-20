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
import project.mainframe.api.task.dto.boardList.BoardListRequest;
import project.mainframe.api.task.dto.boardList.BoardListResponse;
import project.mainframe.api.task.services.BoardListService;

/**
 * Board list controller.
 */
@RestController
@RequestMapping("/api/v1/user/board-lists")
public class BoardListController {
    
    /**
     * Board list service.
     */
    private BoardListService boardListService;

    /**
     * Constructor.
     * @param boardListService The board list service.
     */
    public BoardListController(BoardListService boardListService) {
        this.boardListService = boardListService;
    }

    /**
     * Find all board lists.
     * @param boardId The board id.
     * @param actor The actor.
     * @return The list of responses.
     */
    @GetMapping("/board/{boardId}")
    public List<BoardListResponse> findAllByBoardId(@PathVariable Long boardId, @Authorization User actor) {
        return boardListService.findAllByBoardId(boardId, actor);
    }

    /**
     * Find by id.
     * @param id The id.
     * @param actor The actor.
     * @return The response.
     */
    @GetMapping("/{id}")
    public BoardListResponse findById(@PathVariable Long id, @Authorization User actor) {
        return boardListService.findById(id, actor);
    }

    /**
     * Create a new board list.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    @PostMapping
    public BoardListResponse create(@RequestBody BoardListRequest request, @Authorization User actor) {
        return boardListService.create(request, actor);
    }

    /**
     * Update a board list.
     * @param id The id.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    @PutMapping("/{id}")
    public BoardListResponse update(@PathVariable Long id, @RequestBody BoardListRequest request, @Authorization User actor) {
        return boardListService.update(id, request, actor);
    }

    /**
     * Delete a board list.
     * @param id The id.
     * @param actor The actor.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @Authorization User actor) {
        boardListService.delete(id, actor);
    }
}

