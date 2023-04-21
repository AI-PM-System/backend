package project.mainframe.api.project.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import project.mainframe.api.project.dto.event.EventRequest;
import project.mainframe.api.project.dto.event.EventResponse;
import project.mainframe.api.project.entities.Event;
import project.mainframe.api.project.entities.Project;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.repositories.EventRepository;
import project.mainframe.api.project.repositories.ProjectRepository;

/**
 * Event service.
 */
@Service
public class EventService {

    /**
     * The event repository to use for CRUD operations.
     */
    private EventRepository eventRepository;

    /**
     * The project repository to use for CRUD operations.
     */
    private ProjectRepository projectRepository;    

    /**
     * User Project Restriction service.
     */
    private UserProjectRestrictionService userProjectRestrictionService;

    /**
     * Constructor.
     * 
     * @param eventRepository The event repository to use for CRUD operations.
     * @param projectRepository The project repository.
     * @param userProjectRestrictionService The user project restriction service.
     */
    public EventService(
        EventRepository eventRepository,
        ProjectRepository projectRepository,
        UserProjectRestrictionService userProjectRestrictionService
    ) {
        this.eventRepository = eventRepository;
        this.projectRepository = projectRepository;
        this.userProjectRestrictionService = userProjectRestrictionService;
    }

    /**
     * Find all events.
     * @param userId The user id.
     * @param request The request.
     * @return The list of responses.
     */
    public List<EventResponse> findAllByProjectId(Long projectId, User actor) {
        if (userProjectRestrictionService.isNotMember(projectId, actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        return eventRepository
            .findAllByProjectId(projectId)
            .stream()
            .map(EventResponse::new)
            .toList();
    }

    /**
     * Find event by id.
     * @param id The id.
     * @param userId The user id.
     * @param request The request.
     * @return The response.
     */
    public EventResponse findById(Long id, User actor) {
        Event event = eventRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found."));

        if (userProjectRestrictionService.isNotMember(event.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        return new EventResponse(event);
    }

    /**
     * Create event.
     * @param projectId The project id.
     * @param userId The user id.
     * @param request The request.
     * @return The response.
     */
    public EventResponse create(EventRequest request, User actor) {
        if (userProjectRestrictionService.isNotMember(request.getProjectId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        Project project = projectRepository
            .findById(request.getProjectId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found."));

        Event event = mapToEntity(request);
        event.setProject(project);

        return new EventResponse(eventRepository.save(event));
    }

    /**
     * Update event.
     * @param id The id.
     * @param userId The user id.
     * @param request The request.
     * @return The response.
     */
    public EventResponse update(Long id, User actor, EventRequest request) {
        Event event = eventRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found."));

        if (userProjectRestrictionService.isNotMember(event.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        event.setName(request.getName());
        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());
        event.setLocation(request.getLocation());
        event.setAgenda(request.getAgenda());

        return new EventResponse(eventRepository.save(event));
    }

    /**
     * Delete event.
     * @param id The id.
     * @param userId The user id.
     * @param request The request.
     * @return The response.
     */
    public void delete(Long id, User actor) {
        Event event = eventRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found."));

        if (userProjectRestrictionService.isNotMember(event.getProject().getId(), actor.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Actor is not a member of the project.");
        }

        eventRepository.delete(event);
    }

    /**
     * Map to entity.
     * @param request
     * @return The entity.
     */
    private Event mapToEntity(EventRequest request) {
        Event event = new Event();
        event.setName(request.getName());
        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());
        event.setLocation(request.getLocation());
        event.setAgenda(request.getAgenda());

        return event;
    }
}
