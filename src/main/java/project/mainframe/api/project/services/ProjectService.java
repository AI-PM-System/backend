package project.mainframe.api.project.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.project.ProjectRequest;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.repositories.ArtifactRepository;
import project.mainframe.api.project.repositories.EventRepository;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.RoleRepository;

@Service
public class ProjectService extends BaseCrudService<ProjectRequest, ProjectResponse, Project, Long> {
    
    /*
     * The event repository.
     */
    private EventRepository eventRepository;

    /*
     * The artifact repository.
     */
    private ArtifactRepository artifactRepository;

    /*
     * The role repository.
     */
    private RoleRepository roleRepository;

    /*
     * The member repository.
     */
    private MemberRepository memberRepository;

    /*
     * Constructor.
     */
    public ProjectService(
        JpaRepository<Project, Long> jpaRepository,
        EventRepository eventRepository,
        ArtifactRepository artifactRepository,
        RoleRepository roleRepository,
        MemberRepository memberRepository
    ) {
        super(jpaRepository);
        this.eventRepository = eventRepository;
        this.artifactRepository = artifactRepository;
        this.roleRepository = roleRepository;
        this.memberRepository = memberRepository;
    }

    /*
     * Maps an entity to a response.
     * 
     * @param entity
     * @return ProjectResponse
     */
    @Override
    protected ProjectResponse mapToResponse(Project entity) {
        return new ProjectResponse(entity);
    }

    /*
     * Maps a request to an entity.
     * 
     * @param request
     * @return Project
     */
    @Override
    protected Project mapToEntity(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setEvents(getAssociatedEntities(eventRepository, request.getEventIds()));
        project.setArtifacts(getAssociatedEntities(artifactRepository, request.getArtifactIds()));
        project.setRoles(getAssociatedEntities(roleRepository, request.getRoleIds()));
        project.setMembers(getAssociatedEntities(memberRepository, request.getMemberIds()));

        return project;
    }
}
