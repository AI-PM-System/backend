package project.mainframe.api.project.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.role.RoleRequest;
import project.mainframe.api.project.dto.role.RoleResponse;
import project.mainframe.api.project.entities.Role;
import project.mainframe.api.project.repositories.ProjectRepository;

/**
 * Role service.
 */
@Service
public class RoleService extends BaseCrudService<RoleRequest, RoleResponse, Role, Long> {
    
    /**
     * The project repository to use for CRUD operations.
     */
    private ProjectRepository projectRepository;    

    /**
     * Constructor.
     * 
     * @param jpaRepository The repository to use for CRUD operations.
     */
    public RoleService(
        JpaRepository<Role, Long> jpaRepository,
        ProjectRepository projectRepository
    ) {
        super(jpaRepository);
        this.projectRepository = projectRepository;
    }

    /**
     * Maps an entity to a response.
     * 
     * @param entity
     * @return EventResponse
     */
    @Override
    protected RoleResponse mapToResponse(Role entity) {
        return new RoleResponse(entity);
    }

    /**
     * Maps a request to an entity.
     * 
     * @param request
     * @return Event
     */
    @Override
    protected Role mapToEntity(RoleRequest request) {
        Role role = new Role();
        role.setProject(getAssociatedEntity(projectRepository, request.getProjectId()));
        role.setName(request.getName());
        role.setDescription(request.getDescription());

        return role;
    }
}
