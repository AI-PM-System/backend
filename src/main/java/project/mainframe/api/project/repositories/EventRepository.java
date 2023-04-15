package project.mainframe.api.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.mainframe.api.project.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
