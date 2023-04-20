package project.mainframe.api.task.dto.task;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.mainframe.api.project.dto.member.MemberResponse;
import project.mainframe.api.task.entities.Task;

/**
 * Task response.
 */
@AllArgsConstructor
@Getter
@Setter
public class TaskResponse {
    
    /**
     * The id of the task.
     */
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
     * The board list id
     */
    private Long boardListId;

    /**
     * The member
     */
    private MemberResponse member;

    /**
     * The task's due date
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;

    /**
     * no-args constructor
     */
    public TaskResponse() {}

    /**
     * Constructor
     * 
     * @param task The task
     */
    public TaskResponse(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.boardListId = task.getBoardList().getId();
        this.member = task.getMember() != null ? new MemberResponse(task.getMember()) : null;
        this.dueDate = task.getDueDate();
    }
}
