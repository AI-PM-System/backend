package project.mainframe.api.project.dto.event;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.Event;

/**
 * Event response.
 */
@AllArgsConstructor
@Getter
@Setter
public class EventResponse {

    /**
     * The event id
     */
    private Long id;

    /**
     * The project
     */
    private ProjectResponse project;

    /**
     * The event name
     */
    private String name;

    /**
     * The start date time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;

    /**
     * The end date time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
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
     * @param includeProject Whether to include the project.
     */
    public EventResponse(Event event, boolean includeProject) {
        this.id = event.getId();
        this.name = event.getName();
        this.startDateTime = event.getStartDateTime();
        this.endDateTime = event.getEndDateTime();
        this.location = event.getLocation();
        this.agenda = event.getAgenda();
        if (includeProject)
            this.project = new ProjectResponse(event.getProject());
    }

    /**
     * Constructor.
     * @param event The event to map.
     */
    public EventResponse(Event event) {
        this(event, true);
    }

    /**
     * No-args constructor
     */
    public EventResponse() {}
}
