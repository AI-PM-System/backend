package project.mainframe.api.project.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import project.mainframe.api.base.services.BaseCrudService;
import project.mainframe.api.project.dto.event.EventRequest;
import project.mainframe.api.project.dto.event.EventResponse;
import project.mainframe.api.project.entities.Event;
import project.mainframe.api.project.repositories.ProjectRepository;

/**
 * Event service.
 */
@Service
public class EventService extends BaseCrudService<EventRequest, EventResponse, Event, Long> {

    /**
     * The project repository to use for CRUD operations.
     */
    private ProjectRepository projectRepository;    

    /**
     * Constructor.
     * 
     * @param jpaRepository The repository to use for CRUD operations.
     * @param projectRepository The project repository.
     */
    public EventService(
        JpaRepository<Event, Long> jpaRepository,
        ProjectRepository projectRepository
    ) {
        super(jpaRepository);
        this.projectRepository = projectRepository;
    }

    /**
     * Maps an entity to a response.
     * 
     * @param entity The entity to map.
     * @return EventResponse The response.
     */
    @Override
    protected EventResponse mapToResponse(Event entity) {
        return new EventResponse(entity);
    }

    /**
     * Maps a request to an entity.
     * 
     * @param request The request to map.
     * @return Event The entity.
     */
    @Override
    protected Event mapToEntity(EventRequest request) {
        Event event = new Event();
        event.setProject(getAssociatedEntity(projectRepository, request.getProjectId()));
        event.setName(request.getName());
        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());
        event.setLocation(request.getLocation());
        event.setAgenda(request.getAgenda());

        return event;
    }
}
