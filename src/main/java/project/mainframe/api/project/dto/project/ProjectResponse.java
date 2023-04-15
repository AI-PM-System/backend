package project.mainframe.api.project.dto.project;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mainframe.api.project.dto.artifact.ArtifactResponse;
import project.mainframe.api.project.dto.event.EventResponse;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.project.dto.role.RoleResponse;
import project.mainframe.api.project.entities.Project;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectResponse {

    /*
     * The id of the project
     */
    private Long id;

    /*
     * The name of the project
     */
    private String name;

    /*
     * The description of the project
     */
    private String description;

    /*
     * The project's events
     */
    private List<EventResponse> events;

    /*
     * The project's members
     */
    private List<MemberResponse> members;

    /*
     * The project's roles
     */
    private List<RoleResponse> roles;

    /*
     * The project's artifacts
     */
    private List<ArtifactResponse> artifacts;

    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.events = project.getEvents().stream().map(EventResponse::new).collect(Collectors.toList());
        this.members = project.getMembers().stream().map(MemberResponse::new).collect(Collectors.toList());
        this.roles = project.getRoles().stream().map(RoleResponse::new).collect(Collectors.toList());
        this.artifacts = project.getArtifacts().stream().map(ArtifactResponse::new).collect(Collectors.toList());
    }
}
