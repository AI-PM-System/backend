package project.mainframe.api.project.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.member.MemberRequest;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.services.MemberService;

/**
 * Member controller.
 */
@RestController
@RequestMapping("/api/v1/user/members")
public class MemberController extends BaseCrudController<MemberRequest, MemberResponse, Member, Long> {

    /**
     * Constructor.
     * @param baseCrudService Base crud service.
     */
    public MemberController(BaseCrudService<MemberRequest, MemberResponse, Member, Long> baseCrudService) {
        super(baseCrudService);
    }

    /**
     * Find member by project id and user's username.
     * 
     * @param projectId The project id.
     * @param username The username.
     * @return Member response.
     */
    @GetMapping("/project/{projectId}/user/{username}")
    public MemberResponse findByProjectIdAndUsername(@PathVariable Long projectId, @PathVariable String username) {
        MemberService memberService = (MemberService) this.baseCrudService;
        return memberService.findByProjectIdAndUserUsername(projectId, username);
    }
}
