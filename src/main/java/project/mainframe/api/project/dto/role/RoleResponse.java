package project.mainframe.api.project.dto.role;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.Role;

/**
 * Role response.
 */
@AllArgsConstructor
@Getter
@Setter
public class RoleResponse {

    /**
     * The id of the role
     */
    private Long id;

    /**
     * The name of the role
     */    
    private String name;
    
    /**
     * The description of the role
     */
    private String description;
    
    /**
     * The role's members
     */
    private List<MemberResponse> members;

    /**
     * The role's project
     */
    private ProjectResponse project;

    /**
     * Constructor.
     * @param role The role to map.
     * @param includeProject Whether to include the project in the response.
     * @param includeMembers Whether to include the members in the response.
     */
    public RoleResponse(Role role, boolean includeProject, boolean includeMembers) {
        this.id = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
        if (includeMembers) {
            this.members = role.getMembers() != null ? role.getMembers().stream().map(m->new MemberResponse(m, false, false)).collect(Collectors.toList()) : null;
        }
        if (includeProject) {
            this.project = new ProjectResponse(role.getProject());
        }
    }

    /**
     * Constructor.
     * @param role The role to map.
     */
    public RoleResponse(Role role) {
        this(role, true, true);
    }

    /**
     * No-args constructor
     */
    public RoleResponse() {}
}
