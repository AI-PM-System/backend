package project.mainframe.api.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
