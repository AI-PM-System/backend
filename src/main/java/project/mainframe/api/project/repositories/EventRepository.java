package project.mainframe.api.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.Event;

/**
 * Event repository.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * Find all by project id.
     * 
     * @param projectId The project id.
     * @return The events.
     */
    List<Event> findAllByProjectId(Long projectId);

    /**
     * Find by id and project id.
     * 
     * @param id The id.
     * @param projectId The project id.
     * @return The event.
     */
    Event findByIdAndProjectId(Long id, Long projectId);
}
