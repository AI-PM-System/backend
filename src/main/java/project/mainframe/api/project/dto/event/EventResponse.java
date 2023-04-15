package project.mainframe.api.project.dto.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mainframe.api.project.dto.project.ProjectResponse;
import project.mainframe.api.project.entities.Event;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventResponse {

    /*
     * The event id
     */
    private Long id;

    /*
     * The project
     */
    private ProjectResponse project;

    /*
     * The event name
     */
    private String name;

    /*
     * The start date time
     */
    private LocalDateTime startDateTime;

    /*
     * The end date time
     */
    private LocalDateTime endDateTime;

    /*
     * The location
     */
    private String location;

    /*
     * The agenda
     */
    private String agenda;

    public EventResponse(Event event) {
        this.id = event.getId();
        this.project = new ProjectResponse(event.getProject());
        this.name = event.getName();
        this.startDateTime = event.getStartDateTime();
        this.endDateTime = event.getEndDateTime();
        this.location = event.getLocation();
        this.agenda = event.getAgenda();
    }
}
