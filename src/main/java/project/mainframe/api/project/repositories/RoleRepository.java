package project.mainframe.api.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.Role;

/**
 * Role repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find all by project id.
     * 
     * @param projectId The project id.
     * @return The roles.
     */
    List<Role> findAllByProjectId(Long projectId);

    /**
     * Find by id and project id.
     * 
     * @param id The id.
     * @param projectId The project id.
     * @return The role.
     */
    Role findByIdAndProjectId(Long id, Long projectId);
}

