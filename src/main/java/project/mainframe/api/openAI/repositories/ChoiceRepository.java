package project.mainframe.api.openAI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.openAI.entities.Choice;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
