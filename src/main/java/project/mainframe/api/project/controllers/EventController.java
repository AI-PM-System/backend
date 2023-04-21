package project.mainframe.api.project.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.mainframe.api.project.annotations.Authorization;
import project.mainframe.api.project.dto.event.EventRequest;
import project.mainframe.api.project.dto.event.EventResponse;
import project.mainframe.api.project.entities.User;
import project.mainframe.api.project.services.EventService;

/**
 * Event controller.
 */
@RestController
@RequestMapping("/v1/user/events")
public class EventController {

    /**
     * The event service
     */
    private final EventService eventService;

    /**
     * Constructor.
     * @param eventService The event service.
     */
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Find all events by project id.
     * 
     * @param projectId The project id.
     * @param user The user.
     * @return List of event responses.
     */
    @GetMapping("/project/{projectId}")
    public List<EventResponse> findAllByProjectId(@PathVariable Long projectId, @Authorization User user) {
        return eventService.findAllByProjectId(projectId, user);
    }

    /**
     * Find by id
     * 
     * @param id The id.
     * @param user The user.
     * @return The event response.
     */
    @GetMapping("/{id}")
    public EventResponse findById(@PathVariable Long id, @Authorization User user) {
        return eventService.findById(id, user);
    }

    /**
     * Create event.
     * 
     * @param eventRequest The event request.
     * @param user The user.
     * @return The event response.
     */
    @PostMapping
    public EventResponse create(@RequestBody EventRequest eventRequest, @Authorization User user) {
        return eventService.create(eventRequest, user);
    }

    /**
     * Update event.
     * 
     * @param id The id.
     * @param eventRequest The event request.
     * @param user The user.
     * @return The event response.
     */
    @PutMapping("/{id}")
    public EventResponse update(@PathVariable Long id, @RequestBody EventRequest eventRequest, @Authorization User user) {
        return eventService.update(id, user, eventRequest);
    }

    /**
     * Delete event.
     * 
     * @param id The id.
     * @param user The user.
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, @Authorization User user) {
        eventService.delete(id, user);
    }
}
