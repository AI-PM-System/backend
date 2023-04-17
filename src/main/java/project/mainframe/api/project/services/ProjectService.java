package project.mainframe.api.project.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.project.dto.project.ProjectRequest;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.repositories.ArtifactRepository;
import project.mainframe.api.project.repositories.EventRepository;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.RoleRepository;

/**
 * Project service.
 */
@Service
public class ProjectService extends BaseCrudService<ProjectRequest, ProjectResponse, Project, Long> {
    
    /**
     * The event repository.
     */
    private EventRepository eventRepository;

    /**
     * The artifact repository.
     */
    private ArtifactRepository artifactRepository;

    /**
     * The role repository.
     */
    private RoleRepository roleRepository;

    /**
     * The member repository.
     */
    private MemberRepository memberRepository;

    /**
     * The chat repository.
     */
    private ChatRepository chatRepository;

    /**
     * Constructor.
     * 
     * @param jpaRepository The repository to use for CRUD operations.
     * @param eventRepository The event repository.
     * @param artifactRepository The artifact repository.
     * @param roleRepository The role repository.
     * @param memberRepository The member repository.
     * @param chatRepository The chat repository.
     */
    public ProjectService(
        JpaRepository<Project, Long> jpaRepository,
        EventRepository eventRepository,
        ArtifactRepository artifactRepository,
        RoleRepository roleRepository,
        MemberRepository memberRepository,
        ChatRepository chatRepository
    ) {
        super(jpaRepository);
        this.eventRepository = eventRepository;
        this.artifactRepository = artifactRepository;
        this.roleRepository = roleRepository;
        this.memberRepository = memberRepository;
        this.chatRepository = chatRepository;
    }

    /**
     * Maps an entity to a response.
     * 
     * @param entity The entity to map.
     * @return ProjectResponse response
     */
    @Override
    protected ProjectResponse mapToResponse(Project entity) {
        // Get the main chat for the project.
        List<Chat> mainChats = chatRepository.findAllByTypeAndProjectId(ChatType.MAIN, entity.getId());
        Chat mainChat = mainChats.size() > 0 ? mainChats.get(0) : null;

        return new ProjectResponse(entity, mainChat);
    }

    /**
     * Maps a request to an entity.
     * 
     * @param request The request to map.
     * @return Project entity
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
