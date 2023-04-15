package project.mainframe.api.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.User;

/**
 * User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}