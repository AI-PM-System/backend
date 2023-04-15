package project.mainframe.api.project.dto.member;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Member request.
 */
@AllArgsConstructor
@Getter
@Setter
public class MemberRequest {

    /**
     * The member's id
     */
    private Long id;

    /**
     * A member's role ids
     */
    private List<Long> roleIds;

    /**
     * The member's project id
     */
    private Long projectId;

    /**
     * The member's user defined by username
     */
    private String username;

    /**
     * No-args constructor
     */
    public MemberRequest() {}
}
