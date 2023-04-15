package project.mainframe.api.project.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * An event defines a meeting or other event 
 * that is related to a project.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
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
    @OneToOne(optional = false)
    private Project project;

    /**
     * What is the name of the meeting?
     */
    @Column(nullable = false)
    private String name;

    /**
     * What date and time is the meeting?
     */
    @Column(nullable = false)
    private LocalDateTime startDateTime;

    /**
     * When is the meeting over?
     */
    @Column(nullable = false)
    private LocalDateTime endDateTime;

    /**
     * What is the location of the meeting?
     */
    @Column(nullable = false)
    private String location;

    /**
     * What is the agenda of the meeting?
     */
    @Column(nullable = false)
    private String agenda;
}
