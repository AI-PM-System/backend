package project.mainframe.api.project.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * An event defines a meeting or other event 
 * that is related to a project.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "events")
public class Event {
    
    /**
     * The id of the event
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * One meeting belongs to one project
     */
    @OneToOne()
    private Project project;

    /**
     * What is the name of the meeting?
     */
    @Column(nullable = false)
    private String name;

    /**
     * What date and time is the meeting?
     */
    @Column(nullable = true)
    private LocalDateTime startDateTime;

    /**
     * When is the meeting over?
     */
    @Column(nullable = true)
    private LocalDateTime endDateTime;

    /**
     * What is the location of the meeting?
     */
    @Column(nullable = true)
    private String location;

    /**
     * What is the agenda of the meeting?
     */
    @Column(nullable = true)
    private String agenda;

    /**
     * No-args constructor
     */
    public Event() {}
}
