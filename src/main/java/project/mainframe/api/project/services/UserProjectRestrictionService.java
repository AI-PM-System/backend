package project.mainframe.api.project.services;

import org.springframework.stereotype.Service;

import project.mainframe.api.project.repositories.MemberRepository;
import project.mainframe.api.project.repositories.ProjectRepository;

/**
 * User project restriction service.
 */
@Service
public class UserProjectRestrictionService {

    /**
     * The member repository
     */
    private MemberRepository memberRepository;

    /**
     * The project repository
     */
    private ProjectRepository projectRepository;

    /**
     * Constructor.
     * 
     * @param memberRepository The member repository.
     * @param projectRepository The project repository.
     */
    public UserProjectRestrictionService(
        MemberRepository memberRepository,
        ProjectRepository projectRepository
    ) {
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }

    /**
     * Checks if the user is a member of the project.
     * 
     * @param username The username.
     * @param projectId The project id.
     * @return True if the user is a member of the project, false otherwise.
     */
    public boolean isMember(Long projectId, String username) {
        return memberRepository.findByProjectIdAndUserUsername(projectId, username) != null;
    }

    /**
     * Check if user is not a member of the project.
     * 
     * @param username The username.
     * @param projectId The project id.
     * @return True if the user is not a member of the project, false otherwise.
     */
    public boolean isNotMember(Long projectId, String username) {
        return !isMember(projectId, username);
    }
}
