package project.mainframe.api.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import project.mainframe.api.security.entities.Authenticatable;

public interface AuthenticatableRepository extends JpaRepository<Authenticatable, String> {
}
