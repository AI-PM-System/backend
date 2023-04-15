package project.mainframe.api.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.Artifact;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
}