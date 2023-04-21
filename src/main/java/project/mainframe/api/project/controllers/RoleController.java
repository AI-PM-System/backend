package project.mainframe.api.project.controllers;

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
import project.mainframe.api.project.dto.role.RoleRequest;
import project.mainframe.api.project.dto.role.RoleResponse;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.services.RoleService;

/**
 * Role controller.
 */
@RestController
@RequestMapping("/api/v1/user/roles")
public class RoleController {

    /**
     * The role service
     */
    private final RoleService roleService;

    /**
     * Constructor.
     * @param roleService The role service.
     */
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Find all roles by project id.
     * 
     * @param projectId The project id.
     * @param user The user.
     * @return List of role responses.
     */
    @GetMapping("/project/{projectId}")
    public List<RoleResponse> findAllByProjectId(@PathVariable Long projectId, @Authorization User user) {
        return roleService.findAllByProjectId(projectId, user);
    }

    /**
     * Find by id
     * 
     * @param id The id.
     * @param user The user.
     * @return The role response.
     */
    @GetMapping("/{id}")
    public RoleResponse findById(@PathVariable Long id, @Authorization User user) {
        return roleService.findById(id, user);
    }

    /**
     * Create a new role.
     * 
     * @param roleRequest The role request.
     * @param user The user.
     * @return The role response.
     */
    @PostMapping
    public RoleResponse create(@RequestBody RoleRequest roleRequest, @Authorization User user) {
        return roleService.create(roleRequest, user);
    }

    /**
     * Update a role.
     * 
     * @param id The id.
     * @param roleRequest The role request.
     * @param user The user.
     * @return The role response.
     */
    @PutMapping("/{id}")
    public RoleResponse update(@PathVariable Long id, @RequestBody RoleRequest roleRequest, @Authorization User user) {
        return roleService.update(id, roleRequest, user);
    }

    /**
     * Delete a role.
     * 
     * @param id The id.
     * @param user The user.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @Authorization User user) {
        roleService.delete(id, user);
    }
}

