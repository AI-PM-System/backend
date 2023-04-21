package project.mainframe.api.project.generator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import project.mainframe.api.project.generator.entities.GeneratorActor;

public interface GeneratorActorRepository extends JpaRepository<GeneratorActor, Long> {
}
