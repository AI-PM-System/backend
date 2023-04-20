package project.mainframe.api.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.Project;

/**
 * Project repository.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Find a project by name.
     * 
     * @param name The name of the project.
     * @return Project
     */
    Project findByName(String name);

    /**
     * Find a project by a member id.
     *
     * @param memberId The id of the member.
     * @return Project
     */
    Project findByMembersId(Long memberId);

    /**
     * Find all by member ids.
     * 
     * @param memberIds The member ids.
     * @return The projects.
     */
    List<Project> findAllByMembersIdIn(List<Long> memberIds);
}
