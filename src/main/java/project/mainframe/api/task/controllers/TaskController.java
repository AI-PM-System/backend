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
import project.mainframe.api.task.dto.task.TaskRequest;
import project.mainframe.api.task.dto.task.TaskResponse;
import project.mainframe.api.task.services.TaskService;

/**
 * Task controller.
 */
@RestController
@RequestMapping("/v1/user/tasks")
public class TaskController {
    
    /**
     * Task service.
     */
    private TaskService taskService;

    /**
     * Constructor.
     * @param taskService The task service.
     */
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Find all tasks.
     * @param boardListId The board list id.
     * @param actor The actor.
     * @return The list of responses.
     */
    @GetMapping("/board-list/{boardListId}")
    public List<TaskResponse> findAllByBoardListId(@PathVariable Long boardListId, @Authorization User actor) {
        return taskService.findAllByBoardListId(boardListId, actor);
    }

    /**
     * Find by id.
     * @param id The id.
     * @param actor The actor.
     * @return The response.
     */
    @GetMapping("/{id}")
    public TaskResponse findById(@PathVariable Long id, @Authorization User actor) {
        return taskService.findById(id, actor);
    }

    /**
     * Create a new task.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    @PostMapping
    public TaskResponse create(@RequestBody TaskRequest request, @Authorization User actor) {
        return taskService.create(request, actor);
    }

    /**
     * Update a task.
     * @param id The id.
     * @param request The request.
     * @param actor The actor.
     * @return The response.
     */
    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @RequestBody TaskRequest request, @Authorization User actor) {
        return taskService.update(id, request, actor);
    }

    /**
     * Delete a task.
     * @param id The id.
     * @param actor The actor.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @Authorization User actor) {
        taskService.delete(id, actor);
    }
}
