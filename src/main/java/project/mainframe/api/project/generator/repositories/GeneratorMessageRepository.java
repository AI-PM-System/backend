package project.mainframe.api.project.generator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.generator.entities.GeneratorMessage;

@Repository
public interface GeneratorMessageRepository extends JpaRepository<GeneratorMessage, Long> {
}
