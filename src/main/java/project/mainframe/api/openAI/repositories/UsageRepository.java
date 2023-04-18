package project.mainframe.api.openAI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.openAI.entities.Usage;

@Repository
public interface UsageRepository extends JpaRepository<Usage, Long> {
}