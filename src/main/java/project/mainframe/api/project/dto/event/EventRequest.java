package project.mainframe.api.project.dto.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.mainframe.api.project.entities.Event;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventRequest {

    /*
     * The event id
     */
    private Long id;
    
    /*
     * The project id
     */
    private Long projectId;

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

    public EventRequest(Event event) {
        id = event.getId();
        projectId = event.getProject().getId();
        name = event.getName();
        startDateTime = event.getStartDateTime();
        endDateTime = event.getEndDateTime();
        location = event.getLocation();
        agenda = event.getAgenda();
    }
}
