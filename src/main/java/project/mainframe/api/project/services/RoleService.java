package project.mainframe.api.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.project.dto.role.RoleRequest;
import project.mainframe.api.project.dto.role.RoleResponse;
import project.mainframe.api.project.entities.Role;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.repositories.ProjectRepository;
import project.mainframe.api.project.repositories.RoleRepository;

/**
 * Role service.
 */
@Service
public class RoleService {

    /**
     * The role repository
     */
    private RoleRepository roleRepository;
    
    /**
     * The project repository to use for CRUD operations.
     */
    private ProjectRepository projectRepository;    

    /**
     * The user project restriction service.
     */
    private UserProjectRestrictionService userProjectRestrictionService;

    /**
     * Constructor.
     * 
     * @param roleRepository The repository to use for CRUD operations.
     * @param projectRepository The project repository to use for CRUD operations.
     * @param userProjectRestrictionService The user project restriction service.
     */
    public RoleService(
        RoleRepository roleRepository,
        ProjectRepository projectRepository,
        UserProjectRestrictionService userProjectRestrictionService
    ) {
        this.roleRepository = roleRepository;
        this.projectRepository = projectRepository;
        this.userProjectRestrictionService = userProjectRestrictionService;
    }

    /**
     * Find all role by project id.
     * 
     * @param projectId The project id.
     * @param user The user.
     * @return The list of roles.
     */
    public List<RoleResponse> findAllByProjectId(Long projectId, User actor) {
        if (userProjectRestrictionService.isNotMember(projectId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        List<Role> roles = roleRepository.findAllByProjectId(projectId);

        return roles.stream().map(RoleResponse::new).collect(Collectors.toList());
    }

    /**
     * Find role by id.
     * 
     * @param id The id.
     * @param user The user.
     * @return The role.
     */
    public RoleResponse findById(Long id, User actor) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found."));

        if (userProjectRestrictionService.isNotMember(role.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        return new RoleResponse(role);
    }

    /**
     * Create role.
     * 
     * @param request The request.
     * @param user The user.
     * @return The role.
     */
    public RoleResponse create(RoleRequest request, User actor) {
        if (userProjectRestrictionService.isNotMember(request.getProjectId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        Role role = mapToEntity(request);
        role.setProject(projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found.")));

        return new RoleResponse(roleRepository.save(role));
    }

    /**
     * Update role.
     * 
     * @param id The id.
     * @param request The request.
     * @param user The user.
     * @return The role.
     */
    public RoleResponse update(Long id, RoleRequest request, User actor) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found."));

        if (userProjectRestrictionService.isNotMember(role.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        return new RoleResponse(roleRepository.save(role));
    }

    /**
     * Delete role.
     * 
     * @param id The id.
     * @param user The user.
     */
    public void delete(Long id, User actor) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found."));

        if (userProjectRestrictionService.isNotMember(role.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        roleRepository.delete(role);
    }

    private Role mapToEntity(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());

        return role;
    }
}
