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
     */
    public RoleResponse(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        this.description = role.getDescription();
        this.members = role.getMembers().stream().map(MemberResponse::new).collect(Collectors.toList());
        this.project = new ProjectResponse(role.getProject());
    }

    /**
     * No-args constructor
     */
    public RoleResponse() {}
}
