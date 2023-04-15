package project.mainframe.api.project.dto.member;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.dto.role.RoleResponse;
import project.mainframe.api.project.dto.user.UserResponse;
import project.mainframe.api.project.entities.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberResponse {
    /*
     * The member's id
     */
    private Long id;

    /*
     * A member's roles
     */
    private List<RoleResponse> roles;

    /*
     * The member's project
     */
    private ProjectResponse project;

    /*
     * The member's user
     */
    private UserResponse user;

    /*
     * Whether the member is an AI
     */
    private boolean isAI;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.roles = member.getRoles().stream().map(RoleResponse::new).collect(Collectors.toList());
        this.project = new ProjectResponse(member.getProject());
        this.user = new UserResponse(member.getUser());
        this.isAI = member.isAI();
    }
}
