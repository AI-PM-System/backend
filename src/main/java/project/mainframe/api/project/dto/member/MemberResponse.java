package project.mainframe.api.project.dto.member;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.dto.role.RoleResponse;
import project.mainframe.api.project.dto.user.UserResponse;
import project.mainframe.api.project.entities.Member;

/**
 * Member response.
 */
@AllArgsConstructor
@Getter
@Setter
public class MemberResponse {

    /**
     * The member's id
     */
    private Long id;

    /**
     * A member's roles
     */
    private List<RoleResponse> roles;

    /**
     * The member's project
     */
    private ProjectResponse project;

    /**
     * The member's user
     */
    private UserResponse user;

    /**
     * Whether the member is an AI
     */
    private boolean isAI;

    /**
     * Whether the member is a project admin
     */
    private boolean admin;

    /**
     * Constructor.
     * @param member The member to map.
     * @param includeProject Whether to include the project in the response.
     */
    public MemberResponse(Member member, boolean includeProject, boolean includeRoles) {
        this.id = member.getId();
        this.user = member.getUser() != null ? new UserResponse(member.getUser()) : null;
        this.isAI = member.isAI();
        this.admin = member.isAdmin();
        if (includeProject) {
            this.project = new ProjectResponse(member.getProject());
        }
        if (includeRoles) {
            this.roles = member.getRoles().stream().map(r->new RoleResponse(r, includeProject, false)).collect(Collectors.toList());
        }
    }

    /**
     * Constructor.
     * @param member The member to map.
     */
    public MemberResponse(Member member) {
        this(member, true, true);
    }

    /**
     * No-args constructor
     */
    public MemberResponse() {}
}
