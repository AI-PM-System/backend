package project.mainframe.api.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.Role;

/**
 * Role repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}

