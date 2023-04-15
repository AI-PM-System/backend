package project.mainframe.api.project.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.member.MemberRequest;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.project.entities.Member;
import project.mainframe.api.project.repositories.ProjectRepository;
import project.mainframe.api.project.repositories.RoleRepository;
import project.mainframe.api.project.repositories.UserRepository;

/**
 * Member service.
 */
@Service
public class MemberService extends BaseCrudService<MemberRequest, MemberResponse, Member, Long>  {
    
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
     * Constructor.
     * 
     * @param jpaRepository The repository to use for CRUD operations.
     */
    public MemberService(
        JpaRepository<Member, Long> jpaRepository,
        ProjectRepository projectRepository,
        UserRepository userRepository,
        RoleRepository roleRepository
    ) {
        super(jpaRepository);
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Maps an entity to a response.
     * 
     * @param entity
     * @return EventResponse
     */
    @Override
    protected MemberResponse mapToResponse(Member entity) {
        return new MemberResponse(entity);
    }

    /**
     * Maps a request to an entity.
     * 
     * @param request
     * @return Event
     */
    @Override
    protected Member mapToEntity(MemberRequest request) {
        Member member = new Member();
        member.setProject(getAssociatedEntity(projectRepository, request.getProjectId()));
        member.setUser(getAssociatedEntity(userRepository, request.getUsername()));
        member.setRoles(getAssociatedEntities(roleRepository, request.getRoleIds()));

        return member;
    }
}
