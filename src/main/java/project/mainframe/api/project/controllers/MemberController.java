package project.mainframe.api.project.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.project.annotations.Authorization;
import project.mainframe.api.project.dto.member.MemberRequest;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.services.MemberService;

/**
 * Member controller.
 */
@RestController
@RequestMapping("/v1/user/members")
public class MemberController {

    /**
     * The member service
     */
    private final MemberService memberService;

    /**
     * Constructor.
     * @param memberService The member service.
     */
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Find all members by project id.
     * 
     * @param projectId The project id.
     * @return List of member responses.
     */
    @GetMapping("/project/{projectId}")
    public List<MemberResponse> findAllByProjectId(@PathVariable Long projectId, @Authorization User user) {
        return memberService.findAllByProjectId(projectId, user);
    }

    /**
     * Find by id
     * 
     * @param id The id.
     * @return The member response.
     */
    @GetMapping("/{id}")
    public MemberResponse findById(@PathVariable Long id, @Authorization User user) {
        return memberService.findById(id, user);
    }

    /**
     * Find authorized user's member by project id.
     * 
     * @param projectId The project id.
     * @param user The user.
     * @return The member response.
     */
    @GetMapping("/project/{projectId}/me")
    public MemberResponse findMyMemberByProjectId(@PathVariable Long projectId, @Authorization User user) {
        return memberService.findByProjectIdAndUsername(projectId, user.getUsername(), user);
    }

    /**
     * Create a new member.
     * 
     * @param memberRequest The member request.
     * @return The member response.
     */
    @PostMapping
    public MemberResponse create(@RequestBody MemberRequest memberRequest, @Authorization User user) {
        return memberService.create(memberRequest, user);
    }

    /**
     * Update member.
     * 
     * @param id The id.
     * @param memberRequest The member request.
     * @return The member response.
     */
    @PutMapping("/{id}")
    public MemberResponse update(@PathVariable Long id, @RequestBody MemberRequest memberRequest, @Authorization User user) {
        return memberService.update(id, memberRequest, user);
    }

    /**
     * Delete member.
     * 
     * @param id The id.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @Authorization User user) {
        memberService.delete(id, user);
    }
}
