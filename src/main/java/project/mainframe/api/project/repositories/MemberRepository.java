package project.mainframe.api.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.Member;

/**
 * Member repository.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /**
     * Find a member by project id and user's username.
     * 
     * @param projectId The project id of the member.
     * @param username The username of the member.
     * @return Member
     */
    Member findByProjectIdAndUserUsername(Long projectId, String username);
}
