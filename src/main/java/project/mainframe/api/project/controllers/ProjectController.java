package project.mainframe.api.project.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.project.annotations.Authorization;
import project.mainframe.api.project.dto.project.ProjectRequest;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.services.ProjectService;

/**
 * Project controller.
 */
@RestController
@RequestMapping("/v1/user/projects")
public class ProjectController {

    /**
     * The project service
     */
    private final ProjectService projectService;

    /**
     * Constructor.
     * @param projectService The project service.
     */
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Find all projects by user
     * 
     * @param user The user.
     * @return List of project responses.
     */
    @GetMapping
    public List<ProjectResponse> findAll(@Authorization User user) {
        return projectService.findAll(user);
    }

    /**
     * Find by id
     * 
     * @param id The id.
     * @param user The user.
     * @return The project response.
     */
    @GetMapping("/{id}")
    public ProjectResponse findById(@PathVariable Long id, @Authorization User user) {
        return projectService.findById(id, user);
    }

    /**
     * Update project
     * 
     * @param id The id.
     * @param projectRequest The project request.
     * @param user The user.
     * @return The project response.
     */
    @PutMapping("/{id}")
    public ProjectResponse update(@PathVariable Long id, @RequestBody ProjectRequest projectRequest, @Authorization User user) {
        return projectService.update(id, projectRequest, user);
    }
}
