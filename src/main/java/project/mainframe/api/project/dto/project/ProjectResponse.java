package project.mainframe.api.project.dto.project;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.chat.dto.chat.ChatResponse;
import project.mainframe.api.chat.entities.Chat;
import project.mainframe.api.project.dto.artifact.ArtifactResponse;
import project.mainframe.api.project.dto.event.EventResponse;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.project.dto.role.RoleResponse;
import project.mainframe.api.project.entities.Project;

/**
 * Project response.
 */
@AllArgsConstructor
@Getter
@Setter
public class ProjectResponse {

    /**
     * The id of the project
     */
    private Long id;

    /**
     * The name of the project
     */
    private String name;

    /**
     * The description of the project
     */
    private String description;

    /**
     * The project's events
     */
    private List<EventResponse> events;

    /**
     * The project's members
     */
    private List<MemberResponse> members;

    /**
     * The project's roles
     */
    private List<RoleResponse> roles;

    /**
     * The project's artifacts
     */
    private List<ArtifactResponse> artifacts;

    /**
     * The project's chats
     */
    private ChatResponse mainChat;

    /**
     * Constructor.
     * @param project The project to map.
     */
    public ProjectResponse(Project project, Chat mainChat) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.events = project.getEvents().stream().map(EventResponse::new).collect(Collectors.toList());
        this.members = project.getMembers().stream().map(MemberResponse::new).collect(Collectors.toList());
        this.roles = project.getRoles().stream().map(RoleResponse::new).collect(Collectors.toList());
        this.artifacts = project.getArtifacts().stream().map(ArtifactResponse::new).collect(Collectors.toList());
        this.mainChat = new ChatResponse(mainChat);
    }

    /**
     * No-args constructor
     */
    public ProjectResponse() {}
}
