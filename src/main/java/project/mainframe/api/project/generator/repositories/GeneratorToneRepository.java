package project.mainframe.api.project.generator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import project.mainframe.api.project.generator.entities.GeneratorTone;

public interface GeneratorToneRepository extends JpaRepository<GeneratorTone, Long> {
}
