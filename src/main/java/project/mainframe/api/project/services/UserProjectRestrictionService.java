package project.mainframe.api.project.services;

import org.springframework.stereotype.Service;

import project.mainframe.api.project.repositories.MemberRepository;

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
     * Constructor.
     * 
     * @param memberRepository The member repository.
     */
    public UserProjectRestrictionService(
        MemberRepository memberRepository
    ) {
        this.memberRepository = memberRepository;
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
