package project.mainframe.api.project.dto.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.entities.Event;

/**
 * Event request.
 */
@AllArgsConstructor
@Getter
@Setter
public class EventRequest {
    
    /**
     * The project id
     */
    private Long projectId;

    /**
     * The event name
     */
    private String name;

    /**
     * The start date time
     */
    private LocalDateTime startDateTime;

    /**
     * The end date time
     */
    private LocalDateTime endDateTime;

    /**
     * The location
     */
    private String location;

    /**
     * The agenda
     */
    private String agenda;

    /**
     * Constructor.
     * @param event The event to map.
     */
    public EventRequest(Event event) {
        projectId = event.getProject().getId();
        name = event.getName();
        startDateTime = event.getStartDateTime();
        endDateTime = event.getEndDateTime();
        location = event.getLocation();
        agenda = event.getAgenda();
    }

    /**
     * No-args constructor
     */
    public EventRequest() {}
}
