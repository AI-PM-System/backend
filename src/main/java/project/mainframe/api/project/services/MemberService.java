package project.mainframe.api.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.project.dto.member.MemberRequest;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.ProjectRepository;
import project.mainframe.api.project.repositories.RoleRepository;
import project.mainframe.api.project.repositories.UserRepository;

/**
 * Member service.
 */
@Service
public class MemberService  {
    
    /**
     * The member repository.
     */
    private MemberRepository memberRepository;

    /**
     * The project repository to use for CRUD operations.
     */
    private ProjectRepository projectRepository;

    /**
     * The user repository to use for CRUD operations.
     */
    private UserRepository userRepository;

    /**
     * The role repository to use for CRUD operations.
     */
    private RoleRepository roleRepository;

    /**
     * User Project Restriction service.
     */
    private UserProjectRestrictionService userProjectRestrictionService;

    /**
     * Constructor.
     * 
     * @param memberRepository The member repository.
     * @param projectRepository The project repository.
     * @param userRepository The user repository.
     * @param roleRepository The role repository.
     * @param userProjectRestrictionService The user project restriction service.
     */
    public MemberService(
        MemberRepository memberRepository,
        ProjectRepository projectRepository,
        UserRepository userRepository,
        RoleRepository roleRepository,
        UserProjectRestrictionService userProjectRestrictionService
    ) {
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userProjectRestrictionService = userProjectRestrictionService;
    }


    /**
     * find all by project id.
     * 
     * @param projectId The project id.
     * @param user The user.
     * @return List<MemberResponse>
     */
    public List<MemberResponse> findAllByProjectId(Long projectId, User actor) {
        if (userProjectRestrictionService.isNotMember(projectId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }
        
        List<Member> members = memberRepository.findAllByProjectId(projectId);
        return members.stream().map(MemberResponse::new).collect(Collectors.toList());
    }

    /**
     * Find by id.
     * 
     * @param id The id.
     * @param user The user.
     * @return MemberResponse
     */
    public MemberResponse findById(Long id, User actor) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found."));
        
        if (userProjectRestrictionService.isNotMember(member.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        return new MemberResponse(member);
    }

    /**
     * Find by project id and username.
     * 
     * @param projectId The project id.
     * @param username The username.
     * @param user The user.
     * @return MemberResponse
     */
    public MemberResponse findByProjectIdAndUsername(Long projectId, String username, User actor) {
        if (userProjectRestrictionService.isNotMember(projectId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        Member member = memberRepository.findByProjectIdAndUserUsername(projectId, username);

        if (member == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found.");
        }

        return new MemberResponse(member);
    }

    /**
     * Create a member.
     * 
     * @param request The member request.
     * @param user The user.
     * @return MemberResponse
     */
    public MemberResponse create(MemberRequest request, User actor) {
        if (userProjectRestrictionService.isNotMember(request.getProjectId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        Member member = new Member();
        member.setProject(projectRepository.findById(request.getProjectId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found.")));
        member.setUser(userRepository.findById(request.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")));
        member.setRoles(roleRepository.findAllById(request.getRoleIds()));

        return new MemberResponse(memberRepository.save(member));
    }

    /**
     * Update a member.
     * 
     * @param id The id.
     * @param request The member request.
     * @param user The user.
     * @return MemberResponse
     */
    public MemberResponse update(Long id, MemberRequest request, User actor) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found."));
        
        if (userProjectRestrictionService.isNotMember(member.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        member.setRoles(roleRepository.findAllById(request.getRoleIds()));
        member.setUser(userRepository.findById(request.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")));

        return new MemberResponse(memberRepository.save(member));
    }

    /**
     * Delete a member.
     * 
     * @param id The id.
     * @param user The user.
     */
    public void delete(Long id, User actor) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found."));
        
        if (userProjectRestrictionService.isNotMember(member.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        memberRepository.delete(member);
    }
}
