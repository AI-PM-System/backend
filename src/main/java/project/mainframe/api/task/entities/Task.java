package project.mainframe.api.task.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.entities.Member;

/**
 * A task is a unit of work that needs to be done.
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Task {
    
    /**
     * The id of the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * The name of the task.
     */
    private String name;
    
    /**
     * The description of the task.
     */
    private String description;

    /**
     * The task due date.
     */
    private LocalDateTime dueDate;
    
    /**
     * The task belongs to one board list.
     */
    @ManyToOne
    private BoardList boardList;

    /**
     * The task belongs to one member.
     */
    @ManyToOne
    private Member member;
    
    /**
     * No-args constructor.
     */
    public Task() {}
}
