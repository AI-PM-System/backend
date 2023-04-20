package project.mainframe.api.project.repositories;

import java.util.List;

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

    /**
     * Find all by project id.
     * 
     * @param projectId The project id.
     * @return The members.
     */
    List<Member> findAllByProjectId(Long projectId);

    /**
     * Find all by user's username.
     * 
     * @param username The username.
     * @return The members.
     */
    List<Member> findAllByUserUsername(String username);

    /**
     * Find by id and project id.
     * 
     * @param id The id.
     * @param projectId The project id.
     * @return The member.
     */
    Member findByIdAndProjectId(Long id, Long projectId);
}
