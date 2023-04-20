package project.mainframe.api.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.chat.enums.ChatType;
import project.mainframe.api.chat.repositories.ChatRepository;
import project.mainframe.api.project.dto.project.ProjectRequest;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.repositories.EventRepository;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.ProjectRepository;
import project.mainframe.api.project.repositories.RoleRepository;

/**
 * Project service.
 */
@Service
public class ProjectService {

    /**
     * The project repository to use for CRUD operations.
     */
    private ProjectRepository projectRepository;
    
    /**
     * The event repository.
     */
    private EventRepository eventRepository;

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
     * The user project restriction service.
     */
    private UserProjectRestrictionService userProjectRestrictionService;

    /**
     * Constructor.
     * 
     * @param projectRepository The project repository.
     * @param eventRepository The event repository.
     * @param roleRepository The role repository.
     * @param memberRepository The member repository.
     * @param chatRepository The chat repository.
     * @param userProjectRestrictionService The user project restriction service.
     */
    public ProjectService(
        ProjectRepository projectRepository,
        EventRepository eventRepository,
        RoleRepository roleRepository,
        MemberRepository memberRepository,
        ChatRepository chatRepository,
        UserProjectRestrictionService userProjectRestrictionService
    ) {
        this.projectRepository = projectRepository;
        this.eventRepository = eventRepository;
        this.roleRepository = roleRepository;
        this.memberRepository = memberRepository;
        this.chatRepository = chatRepository;
        this.userProjectRestrictionService = userProjectRestrictionService;
    }

    /**
     * Find all projects.
     * 
     * @param user The user.
     * @return The list of projects.
     */
    public List<ProjectResponse> findAll(User user) {
        // Find all the user's member ids
        List<Long> memberIds = memberRepository.findAllByUserUsername(user.getUsername()).stream()
            .map(Member::getId)
            .collect(Collectors.toList());
        // Find all the projects that have a member id in the list
        List<Project> projects = projectRepository.findAllByMembersIdIn(memberIds);

        return projects.stream()
            .map(ProjectResponse::new)
            .collect(Collectors.toList());
    }

    /**
     * Find a project by id.
     * 
     * @param id The id.
     * @param user The user.
     * @return The project.
     */
    public ProjectResponse findById(Long id, User actor) {
        // Find the project
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // Check if the user is a member of the project
        if (!userProjectRestrictionService.isNotMember(id, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project");
        }

        return new ProjectResponse(project);
    }

    /**
     * Update a project.
     * 
     * @param id The id.
     * @param projectRequest The project request.
     * @param user The user.
     */
    public ProjectResponse update(Long id, ProjectRequest projectRequest, User actor) {
        // Find the project
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // Check if the user is a member of the project
        if (!userProjectRestrictionService.isNotMember(id, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project");
        }

        // Update the project
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project = projectRepository.save(project);

        return new ProjectResponse(project);
    }
}
