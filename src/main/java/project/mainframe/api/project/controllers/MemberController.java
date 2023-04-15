package project.mainframe.api.project.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

import project.mainframe.api.base.controllers.BaseCrudController;
import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.member.MemberRequest;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.project.entities.Member;

/**
 * Member controller.
 */
@RequestMapping("/api/members")
public class MemberController extends BaseCrudController<MemberRequest, MemberResponse, Member, Long> {

    /**
     * Constructor.
     * @param baseCrudService
     */
    public MemberController(BaseCrudService<MemberRequest, MemberResponse, Member, Long> baseCrudService) {
        super(baseCrudService);
    }
}
